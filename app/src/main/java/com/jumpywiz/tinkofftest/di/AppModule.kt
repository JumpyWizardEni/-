package com.jumpywiz.tinkofftest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(context: Context) {
    private val context: Context

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    init {
        this.context = context
    }
}