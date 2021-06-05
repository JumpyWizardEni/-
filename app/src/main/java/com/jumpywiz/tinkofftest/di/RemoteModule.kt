package com.jumpywiz.tinkofftest.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jumpywiz.tinkofftest.net.RetrofitService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson) = Retrofit.Builder()
        .baseUrl("https://developerslife.ru/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    @Provides
    fun provideRetrofitService(retrofit:Retrofit) = retrofit.create(RetrofitService::class.java)
    @Provides
    fun provideGsonFactory() = GsonBuilder().setLenient().create()

}