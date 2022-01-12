package com.mypro.suitmedia.network

import com.mypro.suitmedia.model.MainResponse
import com.mypro.suitmedia.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UserResponse
}