package com.emdasoft.mysavings.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emdasoft.mysavings.data.RepositoryImpl
import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.usecases.GetCardListUseCase
import com.emdasoft.mysavings.domain.usecases.SpendMoneyUseCase
import kotlinx.coroutines.launch

class SpendViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val getCardListUseCase = GetCardListUseCase(repository)
    private val spendMoneyUseCase = SpendMoneyUseCase(repository)

    private val _cardList = getCardListUseCase()
    val cardList: LiveData<List<CardItem>>
        get() = _cardList

    private val _shouldScreenClose = MutableLiveData<Boolean>()
    val shouldScreenClose: LiveData<Boolean>
        get() = _shouldScreenClose

    private val _showInputAmountError = MutableLiveData<Boolean>()
    val showInputAmountError: LiveData<Boolean>
        get() = _showInputAmountError

    private val _showChooseCardError = MutableLiveData<Boolean>()
    val showChooseCardError: LiveData<Boolean>
        get() = _showChooseCardError


    fun spendMoney(amountInput: String?, sourceCardInput: Any?) {
        val amount = parseAmount(amountInput)
        val sourceCard = try {
            sourceCardInput as CardItem
        } catch (e: Exception) {
            null
        }
        val isValid = isValid(amount, sourceCard)
        if (isValid) {
            viewModelScope.launch {
                spendMoneyUseCase(amount, sourceCard!!)
                _shouldScreenClose.value = true
            }
        }
    }

    private fun parseAmount(amountInput: String?): Double {
        return try {
            amountInput?.trim()?.toDouble() ?: 0.0
        } catch (e: Exception) {
            0.0
        }
    }

    private fun isValid(amount: Double, card: CardItem?): Boolean {
        var result = true
        if (card == null) {
            result = false
            _showChooseCardError.value = true
        } else {
            if (amount <= 0.0 || amount > card.amount) {
                result = false
                _showInputAmountError.value = true
            }
        }
        return result
    }

    fun resetAmountError() {
        _showInputAmountError.value = false
    }

    fun resetChooseCardError() {
        _showChooseCardError.value = false
    }

}