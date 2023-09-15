package com.sherry.dogapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sherry.dogapp.R

fun loadImageFromURL(context: Context, url: String, view: ImageView){
    Glide.with(context).load(url).placeholder(R.drawable.ic_no_image).into(view)
}