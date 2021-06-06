package com.jumpywiz.tinkofftest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jumpywiz.tinkofftest.presentation.ui.State

@Entity
data class GifEntity(
    var gifURL: String?,
    var label: String,
    var position: Int,
    var state : State,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)