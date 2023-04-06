package com.emdasoft.mysavings.domain.usecases

import com.emdasoft.mysavings.domain.repository.Repository

class GetBalanceByCategoryUseCase(private val repository: Repository) {

    operator fun invoke() = repository.getBalanceByCategory()

}