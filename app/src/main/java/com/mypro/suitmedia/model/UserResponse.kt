package com.mypro.suitmedia.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val data: MutableList<User>
)
