package com.thomas.apps.nhatrosvkltn.model

import java.io.File
import java.io.Serializable

data class Image(
    val id: Int = 0,
    val url: String = "",
    val file: File? = null
) : Serializable