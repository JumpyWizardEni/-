package com.jumpywiz.tinkofftest.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jumpywiz.tinkofftest.db.GifDao
import com.jumpywiz.tinkofftest.helpers.TransactionHelper
import com.jumpywiz.tinkofftest.model.Gif
import com.jumpywiz.tinkofftest.model.GifEntity
import com.jumpywiz.tinkofftest.model.RequestAnswer
import com.jumpywiz.tinkofftest.model.Result
import com.jumpywiz.tinkofftest.net.RetrofitService
import com.jumpywiz.tinkofftest.presentation.ui.State
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

class MainRepository(private val gifDao: GifDao, private val retrofitService: RetrofitService) {

    private var cachedResult: MutableMap<State, RequestAnswer?> =
        mutableMapOf(State.HOT to null, State.BEST to null, State.LATEST to null)

    private var currentPosInPage: MutableMap<State, Int> =
        mutableMapOf(State.HOT to 0, State.BEST to 0, State.LATEST to 0)
    private var currentPage: MutableMap<State, Int> =
        mutableMapOf(State.HOT to 0, State.BEST to 0, State.LATEST to 0)

    suspend fun getNext(current: Int, latest: Int, state: State, cached: Boolean = true): Gif? {
        when (state) {
            State.RANDOM -> {
                val cachedData = getCached(current, state)
                if (cachedData != null) {
                    return cachedData
                }
                var data: Result? = null
                try {
                    data = retrofitService.getRandom()
                } catch (exception: java.net.SocketTimeoutException) {//No Internet
                }
                if (data != null) {
                    gifDao.insertEntry(TransactionHelper.resultToEntity(data, current, state)!!)
                }
                return TransactionHelper.resultToGif(data, current)
            }
            State.HOT, State.BEST, State.LATEST -> {
                if (cachedResult[state] == null) {
                    Log.d("MainRepository", "downloadNewPage call")
                    return downloadNewPage(current, state)
                } else {
                    Log.d(
                        "MainRepository",
                        "getNext: currentPosInPage = $currentPosInPage, size = ${cachedResult[state]!!.result.size}"
                    )

                    if (currentPosInPage[state]!! >= cachedResult[state]!!.result.size) { // download new page
                        Log.d("MainRepository", "getNext: downloadNewPage call")

                        return downloadNewPage(current, state)
                    } else { //page downloaded
                        Log.d("MainRepository", "getNext: cashed data received")

                        gifDao.insertEntry(
                            TransactionHelper.resultToEntity(
                                cachedResult[state]!!.result[currentPosInPage[state]!!],
                                current,
                                state
                            )!!
                        )
                        if (current >= latest) {
                            currentPosInPage[state] = currentPosInPage[state]!! + 1
                        }
                        return if (cached) {
                            getCached(current, state)
                        } else {
                            TransactionHelper.resultToGif(
                                cachedResult[state]!!.result[currentPosInPage[state]!! - 1],
                                current
                            )
                        }
                    }
                }
            }
        }
    }

    suspend fun getCached(current: Int, state: State): Gif? {
        val data = gifDao.getGif(current, state)
        return TransactionHelper.entityToGif(data)
    }

    private suspend fun downloadNewPage(current: Int, state: State): Gif? {
        var data: RequestAnswer? = null
        try {
            data = retrofitService.getGifFromSection(
                getSectionName(state),
                currentPage[state]!!
            )
        } catch (exception: java.net.SocketTimeoutException) {//No Internet
        }
        cachedResult[state] = data

        if (data != null) {
            gifDao.insertEntry(
                TransactionHelper.resultToEntity(
                    data.result[0],
                    current,
                    state
                )!!
            )
        } else {
            return null
        }
        currentPage[state] = currentPage[state]!! + 1
        currentPosInPage[state] = 1
        return TransactionHelper.resultToGif(data.result[0], current)
    }

    private fun getSectionName(state: State): String {
        return when (state) {
            State.RANDOM -> ""
            State.HOT -> "latest" // Hot API request doesn't work, so I replaced it
            State.BEST -> "top"
            State.LATEST -> "latest"
        }
    }
}
