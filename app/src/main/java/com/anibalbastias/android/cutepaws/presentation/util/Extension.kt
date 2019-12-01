package com.anibalbastias.android.cutepaws.presentation.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anibalbastias.android.cutepaws.R
import com.anibalbastias.android.cutepaws.base.view.Resource
import com.anibalbastias.android.cutepaws.base.view.ResourceState
import com.anibalbastias.android.cutepaws.presentation.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*

fun <T> LiveData<T>.initObserver(lifecycleOwner: LifecycleOwner, observer: (T?) -> Unit) {
    try {
        removeObservers(lifecycleOwner)
        observe(lifecycleOwner, Observer<T> { t -> observer.invoke(t!!) })
    } catch (e: KotlinNullPointerException) {
        e.printStackTrace()
    }

}

fun <T> LiveData<T>.initObserverForever(observer: Observer<T>) {
    removeObserver(observer)
    observeForever { t -> observer.onChanged(t) }
}

@SuppressLint("ShowToast")
fun Activity.getToastTypeFaced(text: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(this, text, duration)
    val typeface = ResourcesCompat.getFont(applicationContext!!, R.font.opensans_regular)
    val toastLayout = toast.view as? LinearLayout
    val toastTV = (toastLayout?.getChildAt(0) as? TextView)
    toastTV?.typeface = typeface
    return toast
}

fun Activity.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) =
    text?.let {
        getToastTypeFaced(text, duration).show()
    }

@SuppressLint("SimpleDateFormat")
fun getTs(): String {
    val tsLong = Date().time / 1000
    return tsLong.toString()
}

fun String.Companion.empty() = ""

fun ImageView.loadImageWithoutPlaceholder(url: String) =
    GlideApp.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(true)
        .into(this)

fun ImageView.loadImageWithPlaceholderCenterCrop(url: String) =
    GlideApp.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(true)
        .into(this)

fun isTablet(context: Context): Boolean = try {
    context.resources.getBoolean(R.bool.isTablet)
} catch (ex: Exception) {
    false
}

fun isPhone(context: Context): Boolean = try {
    !context.resources.getBoolean(R.bool.isTablet)
} catch (ex: Exception) {
    false
}


fun isPortrait(context: Context): Boolean = try {
    context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
} catch (ex: Exception) {
    false
}

fun SwipeRefreshLayout.initSwipe(onSwipeUnit: (() -> Unit)?) {
    this.setColorSchemeColors(ContextCompat.getColor(context, R.color.primaryColor))
    this.setOnRefreshListener { onSwipeUnit?.invoke() }
}

/**
 * Implements a custom observer
 * to a MutableLiveData object
 */
fun <T> Fragment.implementObserver(
    mutableLiveData: MutableLiveData<Resource<T>>,
    successBlock: (T) -> Unit = {},
    loadingBlock: () -> Unit = {},
    errorBlock: (String?) -> Unit = {},
    defaultBlock: () -> Unit = {},
    codeBlock: () -> Unit = {},
    hideLoadingBlock: () -> Unit = {}
) {

    handleObserver(
        mutableLiveData,
        defaultBlock,
        successBlock,
        loadingBlock,
        errorBlock,
        codeBlock,
        hideLoadingBlock
    )
}

private fun <T> Fragment.handleObserver(
    mutableLiveData: MutableLiveData<Resource<T>>,
    defaultBlock: () -> Unit,
    successBlock: (T) -> Unit,
    loadingBlock: () -> Unit,
    errorBlock: (String?) -> Unit,
    codeBlock: () -> Unit,
    hideLoadingBlock: () -> Unit = {}
) {
    mutableLiveData.initObserver(this) {
        handleStateObservers(
            codeBlock,
            it,
            defaultBlock,
            successBlock,
            loadingBlock,
            errorBlock,
            hideLoadingBlock
        )
    }
}

private fun <T> handleStateObservers(
    codeBlock: () -> Unit,
    it: Resource<T>?,
    defaultBlock: () -> Unit,
    successBlock: (T) -> Unit,
    loadingBlock: () -> Unit,
    errorBlock: (String?) -> Unit,
    hideLoadingBlock: () -> Unit = {}
) {
    codeBlock()

    when (it?.status) {
        ResourceState.DEFAULT -> defaultBlock()
        ResourceState.SUCCESS -> {
            hideLoadingBlock()
            successBlock(it.data!!)
        }
        ResourceState.LOADING -> loadingBlock()
        ResourceState.ERROR -> {
            hideLoadingBlock()
            errorBlock(it.message)
        }
        else -> {
        }
    }
}

fun RecyclerView.runLayoutAnimation() {
    layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
    adapter?.notifyDataSetChanged()
    scheduleLayoutAnimation()
}
