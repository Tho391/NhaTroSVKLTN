package com.thomas.apps.nhatrosvkltn.model

import com.thomas.apps.nhatrosvkltn.model.servermodel.CommentResponse
import java.io.Serializable

data class Comment(
    val id: Int = 0,
    var content: String = "",
    var createdAt: String = "",

    var user: User? = null,

    var apartment: Apartment? = null,

    var rating: Float = 0F
) : Serializable {
    fun toCommentResponse(): CommentResponse {
//        return CommentResponse(
//            id,
//            content,
//            user.id,
//            user.getName(),
//            user.avatar,
//            createdAt
//        )
        return CommentResponse(
            idNhatro = id,
            noidung = content,
            idNguoidung = user?.id ?: 0,
            ten = user?.getName() ?: "",
            photo = user?.avatar ?: "",
            date = createdAt,
            content = content,
            userId = user?.id ?: 0,
            vote = rating

        )
    }
}