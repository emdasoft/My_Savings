package com.emdasoft.mysavings.domain.usecases

import com.emdasoft.mysavings.domain.repository.Repository

class GetBalanceUseCase(private val repository: Repository) {

    operator fun invoke() = repository.getBalance()

}