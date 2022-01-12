package com.mypro.suitmedia.model

data class MainResponse<T>(
    var data: T? = null,
    var throwable: Throwable? = null
)