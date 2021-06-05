package com.jumpywiz.tinkofftest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jumpywiz.tinkofftest.model.GifEntity

@Dao
interface GifDao {
    @Query("DELETE FROM gifentity")
    fun deleteAll()

    @Query("SELECT * FROM gifentity WHERE id = :id")
    suspend fun getGif(id: Int): GifEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(resultToEntity: GifEntity)

}