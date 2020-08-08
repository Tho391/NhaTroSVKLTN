package com.thomas.apps.nhatrosvkltn.utils

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import com.google.gson.Gson
import com.thomas.apps.nhatrosvkltn.model.User
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


fun getToken(context: Context): String {
    val sharePreferences: SharedPreferences =
        context.getSharedPreferences("pref_name", Context.MODE_PRIVATE)

    return sharePreferences.get("token", "")
}

//fun isValidEmail(email: String): Boolean {
//    return true
//}

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

fun saveUser(context: Context, user: User) {
    val sharedPreferences = context.getSharedPreferences(
        Constant.SHARE_PREFERENCES_KEY,
        Context.MODE_PRIVATE
    )
    sharedPreferences.put(Constant.CURRENT_USER_KEY, Gson().toJson(user))
}

@Throws(IOException::class)
fun stream2file(`in`: InputStream?, prefix: String, suffix: String? = null): File? {
    val tempFile: File = File.createTempFile(prefix, suffix)
    tempFile.deleteOnExit()
    FileOutputStream(tempFile).use { out -> IOUtils.copy(`in`, out) }
    return tempFile
}

fun getFileName(context: Context, uri: Uri): String? {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor: Cursor? =
            context.contentResolver.query(uri, null, null, null, null)
        cursor.use { it ->
            if (it != null && it.moveToFirst()) {
                result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result
}

fun convertPrice(price: String): String {
    return "%,d".format(price.toInt())
}
