package com.jumpywiz.tinkofftest.model

enum class Source {
    DB, NET
}

data class Gif (
    var gifURL: String?,
    var source: Source,
    var label: String,
    var id: Int
)
