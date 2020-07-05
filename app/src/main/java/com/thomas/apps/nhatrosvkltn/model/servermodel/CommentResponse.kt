package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.thomas.apps.nhatrosvkltn.model.Comment
import com.thomas.apps.nhatrosvkltn.model.User
import java.io.Serializable

data class CommentResponse(
    @SerializedName("idNhatro")
    @Expose
    val idNhatro: Int,
    @SerializedName("Noidung")
    @Expose
    val noidung: String,
    @SerializedName("idNguoidung")
    @Expose
    val idNguoidung: Int?,
    @SerializedName("Ten")
    @Expose
    val ten: String,
    @SerializedName("photo")
    @Expose
    val photo: String?,
    @SerializedName("date")
    @Expose
    val date: String
) : Serializable {
    fun toComment(): Comment {
        return Comment(
            idNhatro,
            noidung,
            date,
            User(null, ten, photo),
            null
        )
    }
}