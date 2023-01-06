package com.spectrotech.magiclifecounter.extensions

import android.app.Activity
import android.content.ContextWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.lifecycle.LifecycleOwner

val View.layoutInflater get() : LayoutInflater = LayoutInflater.from(context)

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.getLifeCycleOwner(): LifecycleOwner? {
    var context = this.context

    while (context is ContextWrapper) {
        if (context is LifecycleOwner) {
            return context
        }
        context = context.baseContext
    }

    return null
}

fun View.getActivity(): Activity? {
    var context = context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context as Activity
        }
        context = context.baseContext
    }
    return null
}

fun View.getActivityRootView(): View? {
    return this.getActivity()?.findViewById<View>(android.R.id.content)?.rootView
}

fun View.setMargins(left: Int = this.marginLeft, top: Int = this.marginTop, right: Int = this.marginRight, bottom: Int = this.marginBottom) {
    if (this.layoutParams is ViewGroup.MarginLayoutParams) {
        val p = this.layoutParams as ViewGroup.MarginLayoutParams
        p.setMargins(left, top, right, bottom)
        this.requestLayout()
    }
}