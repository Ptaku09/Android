package com.example.project.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlayerData::class], version = 7)
abstract class PlayerDB : RoomDatabase() {
    abstract fun myDao(): PlayerDao

    companion object {
        private var DB_INSTANCE: PlayerDB? = null

        @Synchronized
        fun getDatabase(context: Context): PlayerDB? {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDB::class.java,
                    "item_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB_INSTANCE
        }
    }
}