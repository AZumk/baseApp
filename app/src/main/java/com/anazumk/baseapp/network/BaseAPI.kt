package com.anazumk.baseapp.network

import com.anazumk.baseapp.network.model.Data
import retrofit2.Call
import retrofit2.http.GET

interface BaseAPI {

    @GET("regional")
    fun getRegionalIndex(): Call<Data?>?
}