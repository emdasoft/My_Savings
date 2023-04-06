package com.emdasoft.mysavings.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.emdasoft.mysavings.data.database.CardDatabase
import com.emdasoft.mysavings.data.mapper.Mapper
import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.repository.Repository

class RepositoryImpl(application: Application) : Repository {

    private val cardListDao = CardDatabase.getInstance(application).cardListDao()
    private val mapper = Mapper()

    override fun getCardsList(): LiveData<List<CardItem>> {
        return MediatorLiveData<List<CardItem>>().apply {
            addSource(cardListDao.getCardList()) {
                value = mapper.mapDbModelListToEntityList(it)
            }
        }
    }

    override suspend fun getCardItem(itemId: Int): CardItem {
        TODO("Not yet implemented")
    }

    override suspend fun addCard(cardItem: CardItem) {
        cardListDao.addCard(mapper.mapEntityToDbModel(cardItem))
    }

    override suspend fun deleteCard(cardItem: CardItem) {
        TODO("Not yet implemented")
    }

    override suspend fun editCard(cardItem: CardItem) {
        TODO("Not yet implemented")
    }

    override fun getBalance(): LiveData<Double> {
        TODO("Not yet implemented")
    }

    override fun getBalanceByCategory(): LiveData<List<Double>> {
        TODO("Not yet implemented")
    }

    override suspend fun transferMoney(
        amount: Double,
        sourceCard: CardItem,
        destinationCard: CardItem
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun spendMoney(amount: Double, sourceCard: CardItem) {
        TODO("Not yet implemented")
    }

    override suspend fun getMoney(amount: Double, sourceCard: CardItem) {
        TODO("Not yet implemented")
    }

    override fun getBudget(amount: Double): LiveData<List<Double>> {
        TODO("Not yet implemented")
    }
}