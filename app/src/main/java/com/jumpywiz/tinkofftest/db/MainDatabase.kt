package com.jumpywiz.tinkofftest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jumpywiz.tinkofftest.model.GifEntity

@Database(entities = [GifEntity::class], version = 2)
abstract class MainDatabase: RoomDatabase() {
    abstract fun gifDao(): GifDao
    companion object{
        private var instance: MainDatabase? = null
        fun getInstance(context: Context): MainDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    MainDatabase::class.java, "Gif").fallbackToDestructiveMigration().build()
            }
            return instance as MainDatabase
        }
    }

}