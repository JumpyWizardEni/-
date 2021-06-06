package com.jumpywiz.tinkofftest

import com.jumpywiz.tinkofftest.helpers.TransactionHelper
import com.jumpywiz.tinkofftest.model.Gif
import com.jumpywiz.tinkofftest.model.GifEntity
import com.jumpywiz.tinkofftest.model.Source
import com.jumpywiz.tinkofftest.presentation.ui.State
import org.junit.Test
import com.jumpywiz.tinkofftest.model.Result
import org.junit.Assert.*

class TransactionHelperTest {

    @Test
    fun nullObjectShouldReturnNull() {
        assertNull(
            "If entityToGif parameter is null, the result must be null",
            TransactionHelper.entityToGif(null)
        )
        assertNull(
            "If resultToEntity parameter is null, the result must be null",
            TransactionHelper.resultToEntity(null, 0, State.RANDOM)
        )
        assertNull(
            "If resultToGif parameter is null, the result must be null",
            TransactionHelper.resultToGif(null, 0)
        )
    }

    @Test
    fun entityToGifTest() {
        assertEquals(
            "entityToGif returned wrong object",
            TransactionHelper.entityToGif(GifEntity("url", "label", 0, State.RANDOM, 1)),
            Gif("url", Source.DB, "label", 0)
        )
    }

    @Test
    fun resultToGifTest() {
        assertEquals(
            "resultToGif returned wrong object",
            TransactionHelper.resultToGif(Result(gifURL = "url", description = "label"), 0),
            Gif("url", Source.NET, "label", 0)
        )
    }

    @Test
    fun resultToEntityTest() {
        assertEquals(
            "resultToEntity returned wrong object",
            TransactionHelper.resultToEntity(
                Result(gifURL = "url", description = "label"),
                0,
                State.RANDOM
            ), GifEntity("url", "label", 0, State.RANDOM)
        )
    }
}