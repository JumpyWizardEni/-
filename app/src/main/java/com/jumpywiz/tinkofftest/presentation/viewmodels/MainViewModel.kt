package com.jumpywiz.tinkofftest.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.jumpywiz.tinkofftest.model.Gif
import com.jumpywiz.tinkofftest.model.Result
import com.jumpywiz.tinkofftest.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repos: MainRepository): ViewModel() {
    private var data: MutableLiveData<Gif?> = MutableLiveData()
    var current = 1
    var cached = 1
    init {
        viewModelScope.launch{
            data.value = repos.getNext(current)
        }
    }
    fun getData() = data
    fun loadNext() {
        viewModelScope.launch{
            if (current >= cached) {
                data.value = repos.getNext(current + 1)
                cached++
            } else {
                data.value = repos.getPrev(current + 1)
            }
            current++
            Log.d("MainViewModel", "current = $current, cached = $cached")
        }
    }
    fun loadPrev() {
        viewModelScope.launch{
            current--
            data.value = repos.getPrev(current)
            Log.d("MainViewModel", "current = $current, cached = $cached")

        }
    }

    fun loadCurrent() {
        viewModelScope.launch{
            data.value = repos.getNext(current)
            Log.d("MainViewModel", "current = $current, cached = $cached")
        }
    }
}