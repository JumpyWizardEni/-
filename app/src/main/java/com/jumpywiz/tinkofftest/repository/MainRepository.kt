package com.jumpywiz.tinkofftest.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jumpywiz.tinkofftest.db.GifDao
import com.jumpywiz.tinkofftest.helpers.TransactionHelper
import com.jumpywiz.tinkofftest.model.Gif
import com.jumpywiz.tinkofftest.model.GifEntity
import com.jumpywiz.tinkofftest.model.Result
import com.jumpywiz.tinkofftest.net.RetrofitService
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

class MainRepository(private val gifDao: GifDao, private val retrofitService: RetrofitService) {

    suspend fun getNext(current: Int): Gif? {
        var data: Result? = null
        try {
            data = retrofitService.getRandom()
        } catch (exception: java.net.SocketTimeoutException) {//No Internet
        }
        if (data != null) {
            gifDao.insertEntry(TransactionHelper.resultToEntity(data, current)!!)
        }
        return TransactionHelper.resultToGif(data, current)
    }

    suspend fun getPrev(current: Int): Gif? {
        val data = gifDao.getGif(current)
        return TransactionHelper.entityToGif(data)
    }
}
