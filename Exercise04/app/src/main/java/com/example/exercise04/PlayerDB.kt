package com.example.exercise04

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DBPlayer::class], version = 2)
abstract class PlayerDB : RoomDatabase() {
    abstract fun playerDao(): PlayerDao?

    companion object {
        private var DB_INSTANCE: PlayerDB? = null

        @Synchronized
        open fun getDatabase(context: Context): PlayerDB? {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDB::class.java, "players"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return DB_INSTANCE
        }
    }
}