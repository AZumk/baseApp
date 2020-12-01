package com.anazumk.baseapp.network

import com.anazumk.baseapp.network.model.CustomError
import com.anazumk.baseapp.utils.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Retrofit

class NetworkHelper {

    companion object {
        fun getAPI(): BaseAPI {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(JsonParser.getMoshi()))
                .baseUrl("https://api.carbonintensity.org.uk/")
                .build()

          return retrofit.create<BaseAPI>(BaseAPI::class.java)
        }

        inline fun <reified T: Any> execute(call: Call<T?>?, noinline success: (T?) -> Unit, noinline failure: (CustomError) -> Unit) {

            call?.enqueue(object: Callback<T?> {
                override fun onFailure(call: Call<T?>, t: Throwable) {
                    t.printStackTrace()
                    failure(CustomError(0, t.message ?: "unknown error"))
                }

                override fun onResponse(call: Call<T?>, response: Response<T?>) {
                    if(response.isSuccessful) success(response.body())
                    else failure(CustomError(response.code(), response.message()))
                }
            })
        }
    }
}