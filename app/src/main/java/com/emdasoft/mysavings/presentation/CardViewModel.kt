package com.emdasoft.mysavings.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emdasoft.mysavings.data.RepositoryImpl
import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.entity.Category
import com.emdasoft.mysavings.domain.entity.Currency
import com.emdasoft.mysavings.domain.usecases.AddCardItemUseCase
import com.emdasoft.mysavings.domain.usecases.EditCardItemUseCase
import com.emdasoft.mysavings.domain.usecases.GetCardItemUseCase
import kotlinx.coroutines.launch

class CardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val addCardItemUseCase = AddCardItemUseCase(repository)
    private val editCardItemUseCase = EditCardItemUseCase(repository)
    private val getCardItemUseCase = GetCardItemUseCase(repository)

    private val _shouldScreenClose = MutableLiveData<Boolean>()
    val shouldScreenClose: LiveData<Boolean>
        get() = _shouldScreenClose

    private val _showInputTitleError = MutableLiveData<Boolean>()
    val showInputTitleError: LiveData<Boolean>
        get() = _showInputTitleError

    private val _showInputCurrencyError = MutableLiveData<Boolean>()
    val showInputCurrencyError: LiveData<Boolean>
        get() = _showInputCurrencyError

    private val _showInputCategoryError = MutableLiveData<Boolean>()
    val showInputCategoryError: LiveData<Boolean>
        get() = _showInputCategoryError

    private val _showInputAmountError = MutableLiveData<Boolean>()
    val showInputAmountError: LiveData<Boolean>
        get() = _showInputAmountError

    private val _cardItemLD = MutableLiveData<CardItem>()
    val cardItemLD: LiveData<CardItem>
        get() = _cardItemLD

    fun addCard(
        titleInput: String?,
        amountInput: String?,
        currencyInput: String?,
        categoryInput: String?,
    ) {
        viewModelScope.launch {
            val title = parseInputString(titleInput)
            val amount = parseAmount(amountInput)
            val currency = parseInputString(currencyInput)
            val category = parseInputString(categoryInput)
            val isValid = validateInput(title, amount, currency, category)
            if (isValid) {
                val cardItem = CardItem(
                    title,
                    amount,
                    "CASH",
                    Currency.valueOf(currency),
                    Category.valueOf(category)
                )
                addCardItemUseCase(cardItem)
                _shouldScreenClose.value = true
            }
        }
    }

    fun editCard(
        titleInput: String?,
        amountInput: String?,
        currencyInput: String?,
        categoryInput: String?,
    ) {
        viewModelScope.launch {
            val title = parseInputString(titleInput)
            val amount = parseAmount(amountInput)
            val currency = parseInputString(currencyInput)
            val category = parseInputString(categoryInput)
            val isValid = validateInput(title, amount, currency, category)
            if (isValid) {
                _cardItemLD.value?.let {
                    editCardItemUseCase(
                        it.copy(
                            title = title,
                            amount = amount,
                            type = "CASH",
                            currency = Currency.valueOf(currency),
                            category = Category.valueOf(category)
                        )
                    )
                    _shouldScreenClose.value = true
                }
            }
        }
    }

    fun getCardItem(itemId: Int) {
        viewModelScope.launch {
            getCardItemUseCase(itemId).let {
                _cardItemLD.value = it
            }
        }
    }

    private fun validateInput(
        title: String,
        amount: Double,
        currency: String,
        category: String
    ): Boolean {
        var isValid = true
        if (title.isEmpty()) {
            isValid = false
            _showInputTitleError.value = true
        }
        if (amount <= 0.0) {
            isValid = false
            _showInputAmountError.value = true
        }
        if (currency.isEmpty()) {
            isValid = false
            _showInputCurrencyError.value = true
        }
        if (category.isEmpty()) {
            isValid = false
            _showInputCategoryError.value = true
        }
        return isValid
    }

    fun resetInputTitleError() {
        _showInputTitleError.value = false
    }

    fun resetInputAmountError() {
        _showInputAmountError.value = false
    }

    fun resetInputCurrencyError() {
        _showInputCurrencyError.value = false
    }

    fun resetInputCategoryError() {
        _showInputCategoryError.value = false
    }

}