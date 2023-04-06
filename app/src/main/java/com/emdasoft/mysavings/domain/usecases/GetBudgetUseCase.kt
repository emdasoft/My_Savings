package com.emdasoft.mysavings.domain.usecases

import com.emdasoft.mysavings.domain.repository.Repository

class GetBudgetUseCase(private val repository: Repository) {

    operator fun invoke(amount: Double) = repository.getBudget(amount)

}