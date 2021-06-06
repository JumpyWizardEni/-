package com.jumpywiz.tinkofftest.model

import com.google.gson.annotations.SerializedName


data class Result (
    @SerializedName("id") var id: Int = 0,
    @SerializedName("description") var description: String = "",
    @SerializedName("votes") var votes: Int = 0,
    @SerializedName("author") var author: String = "",
    @SerializedName("date") var date: String = "",
    @SerializedName("gifURL") var gifURL: String? = "",
    @SerializedName("gifSize") var gifSize: Int = 0,
    @SerializedName("previewURL") var previewURL: String = "",
    @SerializedName("videoURL") var videoURL: String = "",
    @SerializedName("videoPath") var videoPath: String = "",
    @SerializedName("videoSize") var videoSize: Int = 0,
    @SerializedName("type") var type: String = "",
    @SerializedName("width") var width: String = "",
    @SerializedName("height") var height: String = "",
    @SerializedName("commentsCount") var commentsCount: Int = 0,
    @SerializedName("fileSize") var fileSize: Int = 0,
    @SerializedName("canVote") var canVote: Boolean = false

)