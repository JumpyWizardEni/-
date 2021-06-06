package com.jumpywiz.tinkofftest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jumpywiz.tinkofftest.model.GifEntity
import com.jumpywiz.tinkofftest.presentation.ui.State

@Dao
interface GifDao {
    @Query("DELETE FROM gifentity")
    fun deleteAll()

    @Query("SELECT * FROM gifentity WHERE position = :pos AND state = :state")
    suspend fun getGif(pos: Int, state: State): GifEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(resultToEntity: GifEntity)

}