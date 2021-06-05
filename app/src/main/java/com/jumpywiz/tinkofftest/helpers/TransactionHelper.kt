package com.jumpywiz.tinkofftest.helpers

import com.jumpywiz.tinkofftest.model.Gif
import com.jumpywiz.tinkofftest.model.GifEntity
import com.jumpywiz.tinkofftest.model.Result
import com.jumpywiz.tinkofftest.model.Source

class TransactionHelper {
    companion object{
        fun resultToGif(r: Result?, id: Int): Gif? {
            if (r == null) {
                return null
            }
            return Gif(r.gifURL, Source.NET, r.description, id)
        }

        fun entityToGif(data: GifEntity?): Gif? {
            if (data == null) {
                return null
            }
            return Gif(data.gifURL, Source.DB, data.label, data.id)
        }

        fun resultToEntity(r: Result?, id: Int): GifEntity? {
            if (r == null) {
                return null
            }
            return GifEntity(r.gifURL, r.description, id)
        }
    }
}