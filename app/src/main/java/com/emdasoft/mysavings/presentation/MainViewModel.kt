package com.emdasoft.mysavings.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.emdasoft.mysavings.data.RepositoryImpl
import com.emdasoft.mysavings.domain.usecases.GetCardListUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val getCardListUseCase = GetCardListUseCase(repository)

    init {
        getCardList()
    }

    private fun getCardList() {
        viewModelScope.launch {
            getCardListUseCase()
        }
    }

}