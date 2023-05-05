package com.emdasoft.mysavings.presentation

import androidx.lifecycle.AndroidViewModel

fun AndroidViewModel.parseAmount(amountInput: String?): Double {
    return try {
        amountInput?.trim()?.toDouble() ?: 0.0
    } catch (e: Exception) {
        0.0
    }
}

fun AndroidViewModel.parseInputString(stringInput: String?): String {
    return stringInput?.trim() ?: ""
}
