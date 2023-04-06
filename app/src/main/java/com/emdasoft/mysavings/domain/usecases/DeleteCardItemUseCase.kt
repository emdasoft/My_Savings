package com.emdasoft.mysavings.domain.usecases

import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.repository.Repository

class DeleteCardItemUseCase(private val repository: Repository) {

    suspend operator fun invoke(cardItem: CardItem) = repository.deleteCard(cardItem)

}