package com.emdasoft.mysavings.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emdasoft.mysavings.domain.entity.Category

@Entity("cards")
data class CardItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val amount: Double,
    val type: String,
    val category: String,
)
