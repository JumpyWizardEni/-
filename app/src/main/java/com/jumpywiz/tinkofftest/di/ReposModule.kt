package com.jumpywiz.tinkofftest.di

import com.jumpywiz.tinkofftest.db.GifDao
import com.jumpywiz.tinkofftest.net.RetrofitService
import com.jumpywiz.tinkofftest.repository.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ReposModule {

    @Provides
    @Singleton
    fun provideRepos(gifDao: GifDao, retrofitService: RetrofitService) = MainRepository(gifDao, retrofitService)
}