package com.anazumk.baseapp.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CustomError(
    @Json(name = "code") val code: Int,
    @Json(name = "message") val message: String
)