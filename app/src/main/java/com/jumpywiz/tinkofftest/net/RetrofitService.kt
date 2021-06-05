package com.jumpywiz.tinkofftest.net

import com.example.example.RequestAnswer
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("random?json=true")
    suspend fun getRandom(): Call<RequestAnswer>
    suspend fun getGifFromSection(): Call<RequestAnswer>
}