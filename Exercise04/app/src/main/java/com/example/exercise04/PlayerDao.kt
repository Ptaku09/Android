package com.example.exercise04

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players ORDER BY id ASC")
    fun getAll(): MutableList<DBPlayer>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(player: DBPlayer?)

    @Query("DELETE FROM players")
    fun deleteAll()

    @Delete
    fun delete(player: DBPlayer?)
}