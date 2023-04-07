package com.emdasoft.mysavings.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardListDao {

    @Query("SELECT * FROM cards")
    fun getCardList(): LiveData<List<CardItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(cardItemDbModel: CardItemDbModel)

    @Query("SELECT * FROM cards WHERE id=:itemId LIMIT 1")
    suspend fun getCardItem(itemId: Int): CardItemDbModel

    @Delete
    suspend fun deleteCardItem(cardItemDbModel: CardItemDbModel)

    @Query("SELECT amount FROM cards")
    suspend fun getAllAmounts() : List<Double>


}