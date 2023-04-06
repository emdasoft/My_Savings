package com.emdasoft.mysavings.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardListDao {

    @Query("SELECT * FROM cards")
    fun getCardList(): LiveData<List<CardItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(cardItemDbModel: CardItemDbModel)

}