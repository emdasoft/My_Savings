package com.emdasoft.mysavings.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.emdasoft.mysavings.data.RepositoryImpl
import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.usecases.DeleteCardItemUseCase
import com.emdasoft.mysavings.domain.usecases.GetBalanceUseCase
import com.emdasoft.mysavings.domain.usecases.GetCardListUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val getCardListUseCase = GetCardListUseCase(repository)
    private val deleteCardItemUseCase = DeleteCardItemUseCase(repository)
    private val getBalanceUseCase = GetBalanceUseCase(repository)

    private val _balance = getBalanceUseCase()
    val balance: LiveData<Double>
        get() = _balance

    private val _cardList = getCardListUseCase()
    val cardList: LiveData<List<CardItem>>
        get() = _cardList

    fun deleteItem(cardItem: CardItem) {
        viewModelScope.launch {
            deleteCardItemUseCase(cardItem)
        }
    }

}