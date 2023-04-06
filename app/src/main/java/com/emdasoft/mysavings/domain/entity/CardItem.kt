package com.emdasoft.mysavings.domain.entity

data class CardItem(
    var id: Int = UNDEFINED_ID,
    var title: String,
    var amount: Double,
    var type: String,
    var currency: String,
    var category: String,
) {
    companion object {
        private const val UNDEFINED_ID = 0
    }
}
