package com.visakh.demochatapp.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import coil.load
import com.google.android.material.snackbar.Snackbar

fun Context.longToast(message: String) {
    logThis(message)
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.shortToast(message: String) {
    logThis(message)
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(message: String) {
    logThis(message)
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Fragment.shortToast(message: String) {
    logThis(message)
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun View.longToast(message: String) {
    logThis(message)
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun View.shortToast(message: String) {
    logThis(message)
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun logThis(message: Any, TAG: String = "logThis") {
    println("$TAG -------*--------> $message")
//    Log.d(TAG, "----------->  $message")
}

fun Fragment.setStatusBarColor(color: Int) {
    requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    requireActivity().window.statusBarColor = color
}



fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

@SuppressLint("ResourceType")
inline fun View.snack(
    @IntegerRes messageRes: Int,
    length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit
) {
    snack(resources.getString(messageRes), length, f)
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun View.showSnack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.show()
}

fun View.appBackSnack(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    val snack = Snackbar.make(this, message, length)
    snack.setActionTextColor(Color.parseColor("#FFFFFF"))
    snack.view.setBackgroundColor(Color.parseColor("#0B3089"))
    snack.show()
}

fun View.errorSnack(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    val snack = Snackbar.make(this, message, length)
    snack.setActionTextColor(Color.parseColor("#FFFFFF"))
    snack.view.setBackgroundColor(Color.parseColor("#ad0c17"))
    snack.show()
}

@SuppressLint("ResourceType")
fun Snackbar.action(@IntegerRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

inline fun View.errorSnackAction(
    message: String,
    length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit
) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.setActionTextColor(Color.parseColor("#FFFFFF"))
    snack.view.setBackgroundColor(Color.parseColor("#ad0c17"))
    snack.show()
}



