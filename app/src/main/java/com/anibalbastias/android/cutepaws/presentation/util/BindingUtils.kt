package com.anibalbastias.android.cutepaws.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anibalbastias.android.cutepaws.presentation.util.adapter.base.BaseBindClickHandler
import com.anibalbastias.android.cutepaws.presentation.util.adapter.base.SingleLayoutBindRecyclerAdapter
import com.anibalbastias.android.cutepaws.presentation.util.widget.WrapContentLinearLayoutManager

@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    imageUrl?.let {
        loadImageWithPlaceholderCenterCrop(imageUrl)
    }
}

@BindingAdapter("setImageUrlFull")
fun ImageView.setImageUrlFull(imageUrl: String?) {
    imageUrl?.let {
        loadImage(imageUrl)
    }
}

@BindingAdapter(value = ["loadAdapterData", "loadAdapterLayout", "loadAdapterListener"], requireAll = false)
fun <T> RecyclerView.loadAdapterData(list: MutableList<T>?, layout: Int?, callback: BaseBindClickHandler<T>? = null) {
    context?.run {
        layout?.let { layoutId ->
            layoutManager = GridLayoutManager(context, 2)
            adapter = SingleLayoutBindRecyclerAdapter(layoutId, list, callback)
            runLayoutAnimation()
        }
    }
}