package com.thomas.apps.nhatrosvkltn.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

//Extension for activity
inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)

//for keyboard
fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

/**
 * Extension method to provide show keyboard for View.
 */
fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

/**
 * Extension method to provide hide keyboard for [Activity].
 */
fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(
            Context
                .INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

/**
 * Extension method to provide hide keyboard for [Fragment].
 */
fun Fragment.hideSoftKeyboard() {
    activity?.hideSoftKeyboard()
}

/**
 * Extension method to provide hide keyboard for [View].
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
/**
 * Extension method used to display a [Toast] message to the user.
 */
fun Activity.TOAST(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
fun Fragment.TOAST(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.context, message, duration).show()
}
fun Context.TOAST(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}


/**
 * Extension method use to display a [Snackbar] message to the user.
 */
fun View.displaySnackbar(messageResId: Int, duration: Int = Snackbar.LENGTH_SHORT): Snackbar {
    val snackbar = Snackbar.make(this, messageResId, duration)
    snackbar.show()
    return snackbar
}


fun View.snack(
    messageRes: Int,
    length: Int = Snackbar.LENGTH_LONG,
    f: Snackbar.() -> Unit
) {
    snack(resources.getString(messageRes), length, f)
}

fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()

    snack.show()
}

fun Snackbar.action(actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    action(view.resources.getString(actionRes), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

/**
 * Method used to easily retrieve [WindowManager] from [Context].
 */
fun Context.getWindowManager() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

/**
 * Method used to easily retrieve display size from [Context].
 */
fun Context.getDisplaySize() = Point().apply {
    getWindowManager().defaultDisplay.getSize(this)
}

/**
 * Method used to easily retrieve display size from [View].
 */
fun View.getDisplaySize() = context.getDisplaySize()

/**
 * Return whether Keyboard is currently visible on screen or not.
 *
 * @return true if keyboard is visible.
 */
fun Activity.isKeyboardVisible(): Boolean {
    val r = Rect()

    //r will be populated with the coordinates of your view that area still visible.
    window.decorView.getWindowVisibleDisplayFrame(r)

    //get screen height and calculate the difference with the usable area from the r
    val height = getDisplaySize().y
    val diff = height - r.bottom

    // If the difference is not 0 we assume that the keyboard is currently visible.
    return diff != 0
}

/**
 * Retrieve a decoded bitmap from resources, or null if the image could not be decoded.
 */
fun Context.decodeBitmap(resId: Int): Bitmap? = BitmapFactory.decodeResource(resources, resId)

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

//SharedPreferences
inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
    when (T::class) {
        Boolean::class -> return this.getBoolean(key, defaultValue as Boolean) as T
        Float::class -> return this.getFloat(key, defaultValue as Float) as T
        Int::class -> return this.getInt(key, defaultValue as Int) as T
        Long::class -> return this.getLong(key, defaultValue as Long) as T
        String::class -> return this.getString(key, defaultValue as String) as T
        else -> {
            if (defaultValue is Set<*>) {
                return this.getStringSet(key, defaultValue as Set<String>) as T
            }
        }
    }

    return defaultValue
}

inline fun <reified T> SharedPreferences.put(key: String, value: T): T {
    val editor = this.edit()

    when (T::class) {
        Boolean::class -> editor.putBoolean(key, value as Boolean)
        Float::class -> editor.putFloat(key, value as Float)
        Int::class -> editor.putInt(key, value as Int)
        Long::class -> editor.putLong(key, value as Long)
        String::class -> editor.putString(key, value as String)
        else -> {
            if (value is Set<*>) {
                editor.putStringSet(key, value as Set<String>)
            }
        }
    }

    editor.commit()
    return value
}

fun SharedPreferences.clear() {
    val editor = this.edit()
    editor.clear()
    editor.apply()

}