package com.emdasoft.mysavings.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.emdasoft.mysavings.data.RepositoryImpl
import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.entity.Category
import com.emdasoft.mysavings.domain.entity.Currency
import com.emdasoft.mysavings.domain.usecases.AddCardItemUseCase
import kotlinx.coroutines.launch

class CardItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RepositoryImpl(application)

    private val addCardItemUseCase = AddCardItemUseCase(repository)

    fun addCard(titleInput: String, countInput: Double) {

        viewModelScope.launch {
            val cardItem = CardItem(
                titleInput,
                countInput,
                "BYN",
                Currency.EUR,
                Category.SAVING
            )
            addCardItemUseCase(cardItem)
        }

    }

}