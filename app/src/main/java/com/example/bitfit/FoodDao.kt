package com.example.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAll(): Flow<List<FoodEntity>>
    // fun getAll(): List<FoodEntity>

    @Query("SELECT MIN(calories) FROM food_table")
    fun getMinimum(): Int

    @Query("SELECT MAX(calories) FROM food_table")
    fun getMaximum(): Int

    @Query("SELECT AVG(calories) FROM food_table")
    fun getAverage(): Int

    @Insert
    fun insertAll(items: List<FoodEntity>)

    @Insert
    fun insert(item: FoodEntity)

    @Query("DELETE FROM food_table")
    fun deleteAll()
}