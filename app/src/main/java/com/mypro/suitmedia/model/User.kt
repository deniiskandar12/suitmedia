package com.mypro.suitmedia.model

import com.google.gson.annotations.SerializedName

data class User(
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val avatar: String
)
