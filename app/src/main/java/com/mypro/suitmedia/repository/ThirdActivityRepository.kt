package com.mypro.suitmedia.repository

import com.mypro.suitmedia.network.ApiClient

class ThirdActivityRepository {
    private var services = ApiClient().create()

    suspend fun getUsers(page: Int, perPage: Int) =
        services.getUsers(page, perPage)
}