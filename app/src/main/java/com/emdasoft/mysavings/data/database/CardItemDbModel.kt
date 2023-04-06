package com.emdasoft.mysavings.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cards")
data class CardItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val amount: Double,
    val type: String,
    val category: String,
    val currency: String
)
