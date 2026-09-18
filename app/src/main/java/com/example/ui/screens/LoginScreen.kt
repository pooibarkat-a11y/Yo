package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.GoldAccent
import com.example.viewmodel.AppScreen
import com.example.viewmodel.AppViewModel
import kotlinx.coroutines.launch

/**
 * Pixel-perfect minimalist Authentication Screen inspired by ChatGPT Android login.
 * Clean white/dark adaptive canvas, centered iconic logo and bold branding,
 * and 3 bottom pill-shaped action buttons:
 * 1. "المتابعة بهذا الحساب" (Dark pill with user avatar & Google badge + email)
 * 2. "المتابعة باستخدام حساب Google" (Outlined pill with Google multicolored logo)
 * 3. "تسجيل الدخول بطريقة أخرى" (Outlined pill with custom email/guest modal)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: AppViewModel,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val authProfile by viewModel.googleAuthManager.userProfile.collectAsState()

    var rememberedEmail by remember {
        mutableStateOf(viewModel.googleAuthManager.getRememberedEmail())
    }

    var showOtherLoginSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Handle back button to return safely
    BackHandler {
        onDismiss()
    }

    // Ensure natural RTL orientation for Arabic UI
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                // Top Bar with Close / Skip button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("login_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "إغلاق",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    TextButton(onClick = onDismiss) {
                        Text(
                            text = "تخطي الآن",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Main Content Column
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Spacer pushing the logo to vertical center
                    Spacer(modifier = Modifier.weight(1.0f))

                    // Minimalist Iconic Logo (Centered)
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_tarteel_minimal_logo),
                            contentDescription = "شعار التطبيق",
                            modifier = Modifier.size(62.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // App Title in bold display typography (Like "ChatGPT")
                    Text(
                        text = "ترتيل",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "رتّل وتدبّر واحفظ بحبل الله المتين",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )

                    // Spacer pushing the action buttons towards bottom
                    Spacer(modifier = Modifier.weight(1.2f))

                    // ==========================================
                    // 3 PILL-SHAPED ACTION BUTTONS (BOTTOM SECTION)
                    // ==========================================
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // ----------------------------------------------------
                        // BUTTON 1: "المتابعة بهذا الحساب" (Dark Pill with Avatar)
                        // ----------------------------------------------------
                        Surface(
                            shape = RoundedCornerShape(36.dp),
                            color = Color(0xFF18181B), // Sleek deep dark charcoal/black
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(66.dp)
                                .clickable {
                                    viewModel.googleAuthManager.signInWithRememberedAccount(
                                        email = rememberedEmail,
                                        name = if (rememberedEmail.contains("youssef", ignoreCase = true)) "يوسف بركات" else "المستخدم الكريم"
                                    )
                                    Toast.makeText(context, "مرحباً بك مجدداً! تم تسجيل الدخول 🌟", Toast.LENGTH_SHORT).show()
                                    onDismiss()
                                }
                                .testTag("login_continue_with_account_button")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Right: Profile Circle Avatar with Google Badge
                                Box(
                                    modifier = Modifier.size(46.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Magenta/Pink circle avatar (like in user screenshot "US")
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFFD946EF)), // Pink / Magenta
                                        contentAlignment = Alignment.Center
                                    ) {
                                        val initials = computeInitials(rememberedEmail)
                                        Text(
                                            text = initials,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    }

                                    // Overlapping mini Google badge on bottom-left in RTL
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .offset(x = (-2).dp, y = (2).dp)
                                            .size(18.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                            .padding(2.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_google_logo),
                                            contentDescription = "Google",
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                // Middle/Left: Text Column
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "المتابعة بهذا الحساب",
                                        color = Color.White,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = rememberedEmail,
                                        color = Color(0xFFA1A1AA), // Soft light gray
                                        fontSize = 12.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }

                        // ----------------------------------------------------
                        // BUTTON 2: "المتابعة باستخدام حساب Google" (Outlined Pill)
                        // ----------------------------------------------------
                        Surface(
                            shape = RoundedCornerShape(36.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, Color(0xFFE4E4E7)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        viewModel.googleAuthManager.signInWithGoogle(coroutineScope)
                                        Toast.makeText(context, "تم تسجيل الدخول بحساب Google بنجاح 🌟", Toast.LENGTH_SHORT).show()
                                        onDismiss()
                                    }
                                }
                                .testTag("login_continue_with_google_button")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "المتابعة باستخدام حساب Google",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.ic_google_logo),
                                    contentDescription = "Google",
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        // ----------------------------------------------------
                        // BUTTON 3: "تسجيل الدخول بطريقة أخرى" (Outlined Pill)
                        // ----------------------------------------------------
                        Surface(
                            shape = RoundedCornerShape(36.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, Color(0xFFE4E4E7)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clickable { showOtherLoginSheet = true }
                                .testTag("login_other_methods_button")
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "تسجيل الدخول بطريقة أخرى",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // ====================================================
        // MODAL BOTTOM SHEET: Alternative Login Methods
        // ====================================================
        if (showOtherLoginSheet) {
            ModalBottomSheet(
                onDismissRequest = { showOtherLoginSheet = false },
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                var emailInput by remember { mutableStateOf("") }
                var passwordInput by remember { mutableStateOf("") }
                var isEmailMode by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 36.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "خيارات تسجيل الدخول الأخرى",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "اختر الطريقة التي تفضلها لحفظ ختماتك وإنجازاتك سحابياً",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    if (!isEmailMode) {
                        // Option 1: Login via Email & Password
                        OutlinedButton(
                            onClick = { isEmailMode = true },
                            shape = RoundedCornerShape(28.dp),
                            border = BorderStroke(1.dp, Color(0xFFE4E4E7)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Icon(Icons.Default.Email, contentDescription = null, tint = EmeraldPrimary)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("تسجيل الدخول بالبريد الإلكتروني", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Option 2: Enter Custom Google Account
                        OutlinedButton(
                            onClick = {
                                isEmailMode = true
                                emailInput = "hhrlkhh0@gmail.com"
                            },
                            shape = RoundedCornerShape(28.dp),
                            border = BorderStroke(1.dp, Color(0xFFE4E4E7)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_google_logo),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("كتابة حساب Google آخر يدويًا", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Option 3: Continue as Guest
                        Button(
                            onClick = {
                                showOtherLoginSheet = false
                                Toast.makeText(context, "تم الدخول كزائر كريم مع الحفظ المحلي", Toast.LENGTH_SHORT).show()
                                onDismiss()
                            },
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Icon(Icons.Default.PersonOutline, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("المتابعة كزائر (بدون حساب)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    } else {
                        // Email / Password Form
                        OutlinedTextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            label = { Text("البريد الإلكتروني") },
                            placeholder = { Text("example@gmail.com") },
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = passwordInput,
                            onValueChange = { passwordInput = it },
                            label = { Text("كلمة المرور") },
                            placeholder = { Text("••••••••") },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                if (emailInput.isNotBlank()) {
                                    rememberedEmail = emailInput
                                    val name = emailInput.substringBefore("@").replace(".", " ")
                                    viewModel.googleAuthManager.signInDirect(emailInput, name)
                                    showOtherLoginSheet = false
                                    Toast.makeText(context, "تم تسجيل الدخول بنجاح 🌟", Toast.LENGTH_SHORT).show()
                                    onDismiss()
                                } else {
                                    Toast.makeText(context, "يرجى كتابة البريد الإلكتروني", Toast.LENGTH_SHORT).show()
                                }
                            },
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Text("تسجيل الدخول ومتابعة الحفظ", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(onClick = { isEmailMode = false }) {
                            Text("رجوع للخيارات السابقة", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Computes 2-letter uppercase initials from email, matching the screenshot's "US" badge.
 */
private fun computeInitials(email: String): String {
    if (email.contains("youssef", ignoreCase = true)) return "US"
    val prefix = email.substringBefore("@").filter { it.isLetter() }
    return when {
        prefix.length >= 2 -> prefix.take(2).uppercase()
        prefix.length == 1 -> prefix.uppercase() + "S"
        else -> "US"
    }
}
