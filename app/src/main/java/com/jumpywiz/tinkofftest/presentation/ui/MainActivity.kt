package com.jumpywiz.tinkofftest.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var progressBar: ProgressBar
    private var currentPressed = State.RANDOM

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
        progressBar = findViewById(R.id.progressBar)

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
                    Source.NET -> glideLoad(it, false)
                    Source.DB -> glideLoad(it, true)
                }
                Log.d("MainActivity", "data = $it")
                prevButton.isEnabled = it.id > 1
            }
        })

        hotButton.setOnClickListener {
            hotButton.isActivated = !hotButton.isActivated
            bestButton.isActivated = false
            latestButton.isActivated = false
            when (hotButton.isActivated) {
                true -> {
                    mainViewModel.loadCurrent(State.HOT)
                    showDownload()
                    currentPressed = State.HOT
                }
                false -> {
                    mainViewModel.loadCurrent(State.RANDOM)
                    showDownload()
                    currentPressed = State.RANDOM
                }
            }
        }

        bestButton.setOnClickListener {
            hotButton.isActivated = false
            bestButton.isActivated = !bestButton.isActivated
            latestButton.isActivated = false
            when (bestButton.isActivated) {
                true -> {
                    mainViewModel.loadCurrent(State.BEST)
                    showDownload()
                    currentPressed = State.BEST
                }
                false -> {
                    mainViewModel.loadCurrent(State.RANDOM)
                    showDownload()
                    currentPressed = State.RANDOM
                }
            }
        }

        latestButton.setOnClickListener {
            hotButton.isActivated = false
            bestButton.isActivated = false
            latestButton.isActivated = !latestButton.isActivated
            when (latestButton.isActivated) {
                true -> {
                    mainViewModel.loadCurrent(State.LATEST)
                    showDownload()
                    currentPressed = State.LATEST
                }
                false -> {
                    mainViewModel.loadCurrent(State.RANDOM)
                    showDownload()
                    currentPressed = State.RANDOM
                }
            }
        }

        nextButton.setOnClickListener {
            Log.d("MainActivity", "Next button pressed")
            mainViewModel.loadNext(currentPressed)
            showDownload()
        }

        prevButton.setOnClickListener {
            Log.d("MainActivity", "Previous button pressed")
            mainViewModel.loadPrev(currentPressed)
            showDownload()
        }

        repeatButton.setOnClickListener {
            mainViewModel.loadCurrent(currentPressed)
            showDownload()
        }
    }

    fun showErrorMessage() {
        errorText.visibility = View.VISIBLE
        bottomText.text = ""
        gifView.visibility = View.GONE
        repeatButton.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun restoreImage() {
        errorText.visibility = View.GONE
        gifView.visibility = View.VISIBLE
        repeatButton.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    fun showDownload() {
        progressBar.visibility = View.VISIBLE
        errorText.visibility = View.GONE
        bottomText.text = ""
        gifView.visibility = View.GONE
        repeatButton.visibility = View.GONE
    }

    fun glideLoad(gif: Gif?, cache: Boolean) {
        GlideApp.with(applicationContext).asGif().load(gif!!.gifURL)
            .onlyRetrieveFromCache(cache).listener(object : RequestListener<GifDrawable> {
                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    runOnUiThread {
                        Log.d("MainActivity", "Resource was downloaded")
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
            .centerCrop()
            .into(gifView)
    }

}