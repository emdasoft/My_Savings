package com.emdasoft.mysavings.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database([CardItemDbModel::class], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardListDao(): CardListDao

    companion object {

        private var INSTANCE: CardDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "cards.db"

        fun getInstance(application: Application): CardDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    CardDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}