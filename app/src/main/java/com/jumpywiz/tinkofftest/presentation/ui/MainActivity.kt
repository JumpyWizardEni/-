package com.jumpywiz.tinkofftest.presentation.ui

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
import com.jumpywiz.tinkofftest.Application
import com.jumpywiz.tinkofftest.R
import com.jumpywiz.tinkofftest.helpers.GlideApp
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
                    Source.NET -> GlideApp.with(applicationContext).asGif().load(it.gifURL)
                        .into(gifView)
                    Source.DB -> {
                        GlideApp.with(applicationContext).asGif().load(it.gifURL)
                            .onlyRetrieveFromCache(true)
                            .error(GlideApp.with(applicationContext).asGif().load(it.gifURL))
                            .into(gifView)
                    }
                }
                bottomText.text = it.label
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
    }

    fun showErrorMessage() {
        errorText.visibility = View.VISIBLE
        bottomText.visibility = View.GONE
        gifView.visibility = View.GONE
    }

    fun restoreImage() {
        errorText.visibility = View.GONE
        bottomText.visibility = View.VISIBLE
        gifView.visibility = View.VISIBLE
    }

    fun showDownload() {
        errorText.visibility = View.INVISIBLE
        bottomText.visibility = View.INVISIBLE
        gifView.visibility = View.INVISIBLE
    }

}