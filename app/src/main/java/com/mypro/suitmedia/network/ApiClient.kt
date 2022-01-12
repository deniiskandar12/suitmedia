package com.mypro.suitmedia.network

import com.google.gson.GsonBuilder
import com.mypro.suitmedia.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient() {
    lateinit var retrofit: Retrofit

    fun create(): ApiInterface {
        val gson = GsonBuilder().serializeNulls().setLenient().create()

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(client)
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    fun get(): Retrofit {
        return retrofit
    }
}