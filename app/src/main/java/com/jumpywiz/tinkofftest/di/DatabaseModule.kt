package com.jumpywiz.tinkofftest.di

import android.content.Context
import com.jumpywiz.tinkofftest.db.MainDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDB(context: Context) = MainDatabase.getInstance(context)

    @Provides
    fun provideDAO(db: MainDatabase) = db.gifDao()
}