package com.example.project.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player_table ORDER BY id ASC")
    fun getAllData(): MutableList<PlayerData>?

    @Query("DELETE FROM player_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: PlayerData): Long

    @Delete
    fun delete(item: PlayerData): Int

    @Query("SELECT * FROM player_table WHERE id = :itemId")
    fun getItemById(itemId: Int): PlayerData?

    @Query("SELECT * FROM player_table WHERE id = :itemId")
    fun getItemById(itemId: Long): PlayerData?

    @Update
    fun update(item: PlayerData)
}