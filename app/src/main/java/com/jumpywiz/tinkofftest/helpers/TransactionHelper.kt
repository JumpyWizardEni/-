package com.jumpywiz.tinkofftest.helpers

import com.jumpywiz.tinkofftest.model.Gif
import com.jumpywiz.tinkofftest.model.GifEntity
import com.jumpywiz.tinkofftest.model.Result
import com.jumpywiz.tinkofftest.model.Source
import com.jumpywiz.tinkofftest.presentation.ui.State

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
            return Gif(data.gifURL, Source.DB, data.label, data.position)
        }

        fun resultToEntity(r: Result?, pos: Int, state: State): GifEntity? {
            if (r == null) {
                return null
            }
            return GifEntity(r.gifURL, r.description, pos, state)
        }
    }
}