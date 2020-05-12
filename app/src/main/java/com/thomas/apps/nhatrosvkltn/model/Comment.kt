package com.thomas.apps.nhatrosvkltn.model

import java.util.*

data class Comment(
    val id: Int,
    var content: String,
    var createdAt: String,
    var user: User
)