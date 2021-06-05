package com.jumpywiz.tinkofftest.presentation.ui

import android.graphics.drawable.Drawable
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jumpywiz.tinkofftest.Application
import com.jumpywiz.tinkofftest.R
import com.jumpywiz.tinkofftest.helpers.GlideApp
import com.jumpywiz.tinkofftest.model.Gif
import com.jumpywiz.tinkofftest.model.Source
import com.jumpywiz.tinkofftest.presentation.viewmodels.MainViewModel
import java.io.File
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var errorText: TextView
    private lateinit var bottomText: TextView
    private lateinit var gifView: ImageView
    private lateinit var latestButton: Button
    private lateinit var bestButton: Button
    private lateinit var hotButton: Button
    private lateinit var repeatButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Application.appComponent.inject(activity = this)

        gifView = findViewById(R.id.gifView)
        bottomText = findViewById(R.id.bottomText)
        errorText = findViewById(R.id.errorText)

        latestButton = findViewById(R.id.latestButton)
        bestButton = findViewById(R.id.bestButton)
        hotButton = findViewById(R.id.hotButton)
        nextButton = findViewById(R.id.nextButton)
        prevButton = findViewById(R.id.prevButton)
        repeatButton = findViewById(R.id.repeatButton)

        showDownload()

        mainViewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(MainViewModel::class.java)

        mainViewModel.getData().observe(this, Observer {
            if (it == null) {//Some problem happened, show error message
                Log.e("Error at MainActivity", "Null received")
                showErrorMessage()
            } else {//Data is successfully fetched
                when (it.source) {
                    Source.NET -> glideLoadNet(it)
                    Source.DB -> glideLoadDb(it)
                }
                prevButton.isEnabled = it.id > 1
                restoreImage()
            }
        })

        hotButton.setOnClickListener {
            hotButton.isEnabled = false
            bestButton.isEnabled = true
            latestButton.isEnabled = true
        }

        bestButton.setOnClickListener {
            hotButton.isEnabled = true
            bestButton.isEnabled = false
            latestButton.isEnabled = true
        }

        latestButton.setOnClickListener {
            hotButton.isEnabled = true
            bestButton.isEnabled = true
            latestButton.isEnabled = false
        }

        nextButton.setOnClickListener {
            Log.d("MainActivity", "Next button pressed")
            mainViewModel.loadNext()
            showDownload()
        }

        prevButton.setOnClickListener {
            Log.d("MainActivity", "Previous button pressed")
            mainViewModel.loadPrev()
            showDownload()
        }

        repeatButton.setOnClickListener {
            mainViewModel.loadCurrent()
            showDownload()
        }
    }

    fun showErrorMessage() {
        errorText.visibility = View.VISIBLE
        bottomText.text = ""
        gifView.visibility = View.GONE
        repeatButton.visibility = View.VISIBLE
    }

    fun restoreImage() {
        errorText.visibility = View.GONE
        gifView.visibility = View.VISIBLE
        repeatButton.visibility = View.GONE
    }

    fun showDownload() {
        errorText.visibility = View.INVISIBLE
        bottomText.text = ""
        gifView.visibility = View.INVISIBLE
        repeatButton.visibility = View.GONE
    }

    fun glideLoadDb(gif: Gif?) {
        GlideApp.with(applicationContext).asGif().load(gif!!.gifURL)
            .listener(object : RequestListener<GifDrawable> {
                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    runOnUiThread {
                        restoreImage()
                        bottomText.text = gif.label
                    }
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    runOnUiThread {
                        showErrorMessage()
                    }
                    return false
                }
            })
            .onlyRetrieveFromCache(true)
            .into(gifView)
    }

    fun glideLoadNet(gif: Gif?) {

        GlideApp.with(applicationContext).asGif().load(gif!!.gifURL)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    runOnUiThread {
                        showErrorMessage()

                    }
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    runOnUiThread {
                        restoreImage()
                        bottomText.text = gif.label
                    }
                    return false
                }

            })
            .into(gifView)
    }
}