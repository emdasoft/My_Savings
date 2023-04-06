package com.emdasoft.mysavings.domain.usecases

import com.emdasoft.mysavings.domain.repository.Repository

class GetCardItemUseCase(private val repository: Repository) {

    suspend operator fun invoke(itemId: Int) = repository.getCardItem(itemId)

}