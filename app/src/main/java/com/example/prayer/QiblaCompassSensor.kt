package com.example.prayer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

data class QiblaCompassState(
    val deviceAzimuthDeg: Float,
    val relativeQiblaDeg: Float,
    val isAligned: Boolean,
    val hasSensor: Boolean
)

@Composable
fun rememberQiblaCompass(targetQiblaBearing: Float): QiblaCompassState {
    val context = LocalContext.current
    var deviceAzimuth by remember { mutableFloatStateOf(0f) }
    var hasSensor by remember { mutableStateOf(true) }

    DisposableEffect(context) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        if (sensorManager == null) {
            hasSensor = false
            return@DisposableEffect onDispose {}
        }

        val rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        val accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        if (rotationVectorSensor == null && (accelSensor == null || magSensor == null)) {
            hasSensor = false
            return@DisposableEffect onDispose {}
        }

        val rotationMatrix = FloatArray(9)
        val orientationAngles = FloatArray(3)
        val lastAccelerometer = FloatArray(3)
        val lastMagnetometer = FloatArray(3)
        var lastAccelerometerSet = false
        var lastMagnetometerSet = false

        var smoothedAzimuth = 0f

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return

                var currentAzimuth = 0f
                var validReading = false

                if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    SensorManager.getOrientation(rotationMatrix, orientationAngles)
                    val azRad = orientationAngles[0]
                    currentAzimuth = (Math.toDegrees(azRad.toDouble()).toFloat() + 360f) % 360f
                    validReading = true
                } else if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.size)
                    lastAccelerometerSet = true
                } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                    System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.size)
                    lastMagnetometerSet = true
                }

                if (!validReading && lastAccelerometerSet && lastMagnetometerSet) {
                    val r = FloatArray(9)
                    val i = FloatArray(9)
                    if (SensorManager.getRotationMatrix(r, i, lastAccelerometer, lastMagnetometer)) {
                        SensorManager.getOrientation(r, orientationAngles)
                        val azRad = orientationAngles[0]
                        currentAzimuth = (Math.toDegrees(azRad.toDouble()).toFloat() + 360f) % 360f
                        validReading = true
                    }
                }

                if (validReading) {
                    // Smooth angular transition to prevent jumps at 0/360 boundary
                    var diff = currentAzimuth - smoothedAzimuth
                    while (diff < -180f) diff += 360f
                    while (diff > 180f) diff -= 360f
                    smoothedAzimuth = (smoothedAzimuth + 0.18f * diff + 360f) % 360f
                    deviceAzimuth = smoothedAzimuth
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        if (rotationVectorSensor != null) {
            sensorManager.registerListener(listener, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI)
        } else {
            if (accelSensor != null) sensorManager.registerListener(listener, accelSensor, SensorManager.SENSOR_DELAY_UI)
            if (magSensor != null) sensorManager.registerListener(listener, magSensor, SensorManager.SENSOR_DELAY_UI)
        }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    val relativeDeg = ((targetQiblaBearing - deviceAzimuth) + 360f) % 360f
    val isAligned = relativeDeg < 4.0f || relativeDeg > 356.0f

    return QiblaCompassState(
        deviceAzimuthDeg = deviceAzimuth,
        relativeQiblaDeg = relativeDeg,
        isAligned = isAligned,
        hasSensor = hasSensor
    )
}
