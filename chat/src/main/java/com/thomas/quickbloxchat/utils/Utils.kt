package com.thomas.quickbloxchat.utils

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns

object Utils {
    fun queryName(resolver: ContentResolver, uri: Uri): String? {
        var name = ""
        var returnCursor: Cursor? = null
        try {
            returnCursor = resolver.query(uri, null, null, null, null)!!
            val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
        } catch (e: Exception) {

        } finally {
            returnCursor?.close()
        }

        return name
    }
}