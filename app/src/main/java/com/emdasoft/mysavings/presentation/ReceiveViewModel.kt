package com.emdasoft.mysavings.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emdasoft.mysavings.data.RepositoryImpl
import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.usecases.GetCardListUseCase
import com.emdasoft.mysavings.domain.usecases.ReceiveMoneyUseCase
import kotlinx.coroutines.launch

class ReceiveViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val getCardListUseCase = GetCardListUseCase(repository)
    private val receiveMoneyUseCase = ReceiveMoneyUseCase(repository)

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


    fun getMoney(amountInput: String?, sourceCardInput: CardItem?) {
        val amount = parseAmount(amountInput)
        val isValid = checkForValid(amount, sourceCardInput)
        if (isValid) {
            viewModelScope.launch {
                sourceCardInput?.let {
                    receiveMoneyUseCase(amount, it)
                    _shouldScreenClose.value = true
                }
            }
        }
    }

    private fun checkForValid(amount: Double, sourceCard: CardItem?): Boolean {
        var result = true
        if (sourceCard == null) {
            _showChooseCardError.value = true
            result = false
        }
        if (amount <= 0) {
            _showInputAmountError.value = true
            result = false
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