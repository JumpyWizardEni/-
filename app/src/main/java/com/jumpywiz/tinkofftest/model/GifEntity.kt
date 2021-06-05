package com.jumpywiz.tinkofftest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GifEntity(
    var gifURL: String?,
    var label: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)