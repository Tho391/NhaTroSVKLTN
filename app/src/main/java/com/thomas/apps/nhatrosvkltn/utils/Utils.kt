package com.thomas.apps.nhatrosvkltn.utils

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.google.gson.Gson
import com.thomas.apps.nhatrosvkltn.model.User


fun getToken(context: Context): String {
    val sharePreferences: SharedPreferences =
        context.getSharedPreferences("pref_name", Context.MODE_PRIVATE)

    return sharePreferences.get("token", "")
}

fun isValidEmail(email: String): Boolean {
    return true
}

fun getRealPathFromURI(context: Context, contentURI: Uri): String? {
    val result: String?
    val projection = arrayOf(MediaStore.Images.Media._ID)
    val cursor: Cursor? = context.contentResolver.query(
        contentURI, projection, null,
        null, null
    )
    if (cursor == null) {
        result = contentURI.path
    } else {
        cursor.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media._ID)
        result = cursor.getString(idx)
        cursor.close()
    }
    return result
}

fun getUser(context: Context): User? {
    val sharedPreferences =
        context.getSharedPreferences(Constant.SHARE_PREFERENCES_KEY, Context.MODE_PRIVATE)
    var user: User? = null
    try {
        user =
            Gson().fromJson<User>(
                sharedPreferences.get(Constant.CURRENT_USER_KEY, ""),
                User::class.java
            )
    } catch (e: Exception) {

    }
    return user
}
