package com.jumpywiz.tinkofftest.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jumpywiz.tinkofftest.model.Gif
import com.jumpywiz.tinkofftest.presentation.ui.State
import com.jumpywiz.tinkofftest.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.set

class MainViewModel @Inject constructor(private val repos: MainRepository) : ViewModel() {
    private var data: MutableLiveData<Gif?> = MutableLiveData()
    private var stateMap: MutableMap<State, MutableList<Int>> = mutableMapOf()

    init {
        stateMap[State.RANDOM] = mutableListOf(1, 1)
        stateMap[State.HOT] = mutableListOf(1, 1)
        stateMap[State.LATEST] = mutableListOf(1, 1)
        stateMap[State.BEST] = mutableListOf(1, 1)
        viewModelScope.launch {
            data.value = repos.getNext(stateMap[State.RANDOM]!![0], stateMap[State.RANDOM]!![0], State.RANDOM)
        }
    }

    fun getData() = data
    fun loadNext(state: State){
        viewModelScope.launch {
            if (stateMap[state]!![0] >= stateMap[state]!![1]) {
                data.value = repos.getNext(stateMap[state]!![0] + 1, stateMap[state]!![0] + 1, state)
                stateMap[state]!![1]++
            } else {
                data.value = repos.getCached(stateMap[state]!![0] + 1,  state)
            }
            stateMap[state]!![0]++
            Log.d(
                "MainViewModel",
                "loadNext: current = ${stateMap[state]!![0]}, cached = ${stateMap[state]!![1]}"
            )
        }
    }

    fun loadPrev(state: State) {
        viewModelScope.launch {
            stateMap[state]!![0]--
            data.value = repos.getCached(stateMap[state]!![0], state)
            Log.d(
                "MainViewModel",
                "loadPrev: current = ${stateMap[state]!![0]}, cached = ${stateMap[state]!![1]}"
            )

        }
    }

    fun loadCurrent(state: State, cached: Boolean = true) {
        viewModelScope.launch {
            data.value = repos.getNext(stateMap[state]!![0], stateMap[state]!![1], state, cached)
            Log.d(
                "MainViewModel",
                "loadCurrent: current = ${stateMap[state]!![0]}, cached = ${stateMap[state]!![1]}"
            )
        }
    }

    fun setData(gif: Gif?) {
        data.value = gif
    }
}