package com.emdasoft.mysavings.domain.usecases

import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.repository.Repository

class GetMoneyUseCase(private val repository: Repository) {

    suspend operator fun invoke(
        amount: Double,
        sourceCard: CardItem
    ) =
        repository.getMoney(amount, sourceCard)
}