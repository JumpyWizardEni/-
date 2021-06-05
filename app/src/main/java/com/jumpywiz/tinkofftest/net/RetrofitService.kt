package com.jumpywiz.tinkofftest.net

import com.jumpywiz.tinkofftest.model.RequestAnswer
import com.jumpywiz.tinkofftest.model.Result
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("random?json=true")
    suspend fun getRandom(): Result?
    suspend fun getGifFromSection(): Call<RequestAnswer>
}