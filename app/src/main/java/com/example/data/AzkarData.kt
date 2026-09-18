package com.example.data

import com.example.model.AzkarCategory
import com.example.model.DhikrItem

object AzkarData {
    val categories: List<AzkarCategory> = Azkar132DoorsData.allDoors

    val dhikrOfTheDay: DhikrItem = categories.first().items.first()
}
