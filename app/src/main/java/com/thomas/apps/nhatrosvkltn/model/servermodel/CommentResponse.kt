package com.thomas.apps.nhatrosvkltn.model.servermodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.thomas.apps.nhatrosvkltn.model.Comment
import com.thomas.apps.nhatrosvkltn.model.User
import java.io.Serializable

data class CommentResponse(
    @SerializedName("idNhatro")
    @Expose
    val idNhatro: Int = 0,
    @SerializedName("Noidung")
    @Expose
    val noidung: String? = "",
    @SerializedName("idNguoidung")
    @Expose
    val idNguoidung: Int? = 0,
    @SerializedName("Ten")
    @Expose
    val ten: String? = "",
    @SerializedName("photo")
    @Expose
    val photo: String? = "",
    @SerializedName("date")
    @Expose
    val date: String? = "",

    @SerializedName("content")
    @Expose
    val content: String? = "",

    @SerializedName("Userid")
    @Expose
    val userId: Int? = 0,

    @SerializedName("Vote")
    @Expose
    val vote: Float = 0F
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