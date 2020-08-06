package com.cloudx.ktx.ui

import android.content.Context
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import com.cloudx.ktx.KtxContext

/**
 * Created by Petterp
 * on 2020-01-28
 * Function: Toast
 */


fun Context.toastLong(message: String) {
    toastTime(message, Toast.LENGTH_LONG)
}

fun Context.toastTop(message: String) {
    toastGravity(message, Gravity.TOP)
}

fun Context.toastBootom(message: String) {
    toastGravity(message, Gravity.BOTTOM)
}

fun Context.toastCenter(message: String) {
    toastGravity(message, Gravity.BOTTOM)
}

private fun Context.toastGravity(message: String, gravity: Int) {
    val makeText = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    makeText.setGravity(gravity, 0, 0)
    makeText.show()
}

private fun Context.toastTime(message: String, time: Int) {
    Toast.makeText(this, message, time).show()
}


fun toast(message: String, time: Int = 2000) {
    KtxContext.context.toastTime(message, time)
}
