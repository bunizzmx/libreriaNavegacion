package com.example.librarynavigation.utils

import androidx.core.content.ContextCompat
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.BindingAdapter
import com.example.librarynavigation.R


object MainBindings {

    @BindingAdapter("app:appProgressDrawable")
    @JvmStatic
    fun setProgressDrawable(progress: ContentLoadingProgressBar, appId: Int) {

       val drawableResId = when(appId){
            1-> R.drawable.curved_progress_bar_purple
            else -> R.drawable.curved_progress_bar_orange
        }

        val progressDrawable = ContextCompat.getDrawable(progress.context, drawableResId)
        progress.progressDrawable = progressDrawable
    }
}