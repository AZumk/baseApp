package com.anazumk.baseapp.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

class JsonParser {

    companion object {
        fun getMoshi(): Moshi{
            return  Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        }

        inline fun <reified T>fromJson(json: String, type: Type) : T? {
            return getMoshi().adapter<T>(type).fromJson(json)
        }

        inline fun <reified T>toJson(value: T): String? {
            return getMoshi().adapter<T>(T::class.java).toJson(value)
        }
    }
}