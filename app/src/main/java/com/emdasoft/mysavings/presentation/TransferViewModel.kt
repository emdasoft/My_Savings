package com.emdasoft.mysavings.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.emdasoft.mysavings.data.RepositoryImpl
import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.usecases.GetCardListUseCase
import com.emdasoft.mysavings.domain.usecases.TransferMoneyUseCase

class TransferViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val transferMoneyUseCase = TransferMoneyUseCase(repository)
    private val getCardListUseCase = GetCardListUseCase(repository)

    private val _cardList = getCardListUseCase()
    val cardList: LiveData<List<CardItem>>
        get() = _cardList

    fun transferMoney(
        amountInput: String?,
        sourceCardInput: CardItem?,
        destinationCardInput: CardItem?
    ) {
        val amount = parseAmount(amountInput)
        val isValid = checkForValid(amount, sourceCardInput, destinationCardInput)
        if(isValid) {

        }
    }

    private fun checkForValid(
        amount: Double,
        sourceCard: CardItem?,
        destinationCard: CardItem?
    ): Boolean {
        var result = true
        if (sourceCard == null) {
//            _showChooseSourceCardError.value = true
            result = false
        }
        if (destinationCard == null) {
//            _showChooseSourceDestinationCardError.value = true
            result = false
        }
        if (amount <= 0) {
//            _showInputAmountError.value = true
            result = false
        }
        return result
    }
}