package com.emdasoft.mysavings.domain.repository

import androidx.lifecycle.LiveData
import com.emdasoft.mysavings.domain.entity.CardItem

interface Repository {

    fun getCardsList(): LiveData<List<CardItem>>

    suspend fun getCardItem(itemId: Int): CardItem

    suspend fun addCard(cardItem: CardItem)

    suspend fun deleteCard(cardItem: CardItem)

    suspend fun editCard(cardItem: CardItem)

    fun getBalance(): LiveData<Double>

    fun getBalanceByCategory(): LiveData<List<Double>>

    suspend fun transferMoney(amount: Double, sourceCard: CardItem, destinationCard: CardItem)

    suspend fun spendMoney(amount: Double, sourceCard: CardItem)

    suspend fun receiveMoney(amount: Double, sourceCard: CardItem)

    fun getBudget(amount: Double): LiveData<List<Double>>

}