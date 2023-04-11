package com.emdasoft.mysavings.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.emdasoft.mysavings.data.database.CardDatabase
import com.emdasoft.mysavings.data.database.CardItemDbModel
import com.emdasoft.mysavings.data.mapper.Mapper
import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.repository.Repository
import kotlin.math.roundToInt

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
        return mapper.mapDbModelToEntity(cardListDao.getCardItem(itemId))
    }

    override suspend fun addCard(cardItem: CardItem) {
        cardListDao.addCard(mapper.mapEntityToDbModel(cardItem))
    }

    override suspend fun deleteCard(cardItem: CardItem) {
        cardListDao.deleteCardItem(mapper.mapEntityToDbModel(cardItem))
    }

    override suspend fun editCard(cardItem: CardItem) {
        cardListDao.addCard(mapper.mapEntityToDbModel(cardItem))
    }

    override fun getBalance(): LiveData<Double> {
        return MediatorLiveData<Double>().apply {
            addSource(cardListDao.getCardList()) {
                value = updateCurrentBalance(it)
            }
        }
    }

    private fun updateCurrentBalance(list: List<CardItemDbModel>): Double {
        var total = 0.0
        for (item in list) {
            if (item.currency == "USD" || item.currency == "EUR") {
                total += item.amount
            }
            if (item.currency == "BYN") {
                total += item.amount / USD_RATE
            }
        }
        total = (total * 100).roundToInt() / 100.00
        return total
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
        val newAmount = sourceCard.amount + amount
        val item = sourceCard.copy(amount = newAmount)
        addCard(item)
    }

    override fun getBudget(amount: Double): LiveData<List<Double>> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val USD_RATE = 2.9
    }
}