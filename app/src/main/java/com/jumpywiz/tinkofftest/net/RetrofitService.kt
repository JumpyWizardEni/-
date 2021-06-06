package com.jumpywiz.tinkofftest.net

import com.jumpywiz.tinkofftest.model.RequestAnswer
import com.jumpywiz.tinkofftest.model.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("random?json=true")
    suspend fun getRandom(): Result?

    @GET("{path}/{page}?json=true")
    suspend fun getGifFromSection(
        @Path("path") path: String,
        @Path("page") page: Int
    ): RequestAnswer
}