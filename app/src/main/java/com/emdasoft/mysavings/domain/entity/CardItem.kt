package com.emdasoft.mysavings.domain.entity

data class CardItem(
    val id: Int,
    val title: String,
    val amount: Double,
    val type: String,
    val category: Category,
)
