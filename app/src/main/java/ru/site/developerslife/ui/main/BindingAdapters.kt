package ru.site.developerslife.ui.main

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}