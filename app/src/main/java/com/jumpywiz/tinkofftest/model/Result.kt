package com.jumpywiz.tinkofftest.model

import com.google.gson.annotations.SerializedName


data class Result (
    @SerializedName("id") var id: Int,
    @SerializedName("description") var description: String,
    @SerializedName("votes") var votes: Int,
    @SerializedName("author") var author: String,
    @SerializedName("date") var date: String,
    @SerializedName("gifURL") var gifURL: String?,
    @SerializedName("gifSize") var gifSize: Int,
    @SerializedName("previewURL") var previewURL: String,
    @SerializedName("videoURL") var videoURL: String,
    @SerializedName("videoPath") var videoPath: String,
    @SerializedName("videoSize") var videoSize: Int,
    @SerializedName("type") var type: String,
    @SerializedName("width") var width: String,
    @SerializedName("height") var height: String,
    @SerializedName("commentsCount") var commentsCount: Int,
    @SerializedName("fileSize") var fileSize: Int,
    @SerializedName("canVote") var canVote: Boolean

)