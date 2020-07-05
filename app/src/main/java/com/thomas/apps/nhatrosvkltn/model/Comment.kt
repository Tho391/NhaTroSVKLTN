package com.thomas.apps.nhatrosvkltn.model

import com.google.gson.annotations.SerializedName
import com.thomas.apps.nhatrosvkltn.model.servermodel.CommentResponse
import java.io.Serializable

data class Comment(
    @SerializedName("id")
    val id: Int,
    @SerializedName("content")
    var content: String,
    @SerializedName("createdAt")
    var createdAt: String,
    @SerializedName("user")
    var user: User,

    @SerializedName("apartment")
    var apartment: Apartment?
) : Serializable {
    fun toCommentResponse(): CommentResponse {
        return CommentResponse(
            id,
            content,
            user.id,
            user.getName(),
            user.avatar,
            createdAt
        )
    }
}