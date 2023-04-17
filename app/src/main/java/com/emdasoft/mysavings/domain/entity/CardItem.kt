package com.emdasoft.mysavings.domain.entity

data class CardItem(
    var title: String,
    var amount: Double,
    var type: String = DEFAULT_TYPE,
    var currency: Currency,
    var category: Category,
    var id: Int = UNDEFINED_ID,
) {

    override fun toString(): String {
        return "• $title • ($amount $currency)"
    }

    companion object {
        private const val UNDEFINED_ID = 0
        private const val DEFAULT_TYPE = "CASH"
    }
}
