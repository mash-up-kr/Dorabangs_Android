package com.mashup.dorabangs.data.utils

import android.content.ContentValues.TAG
import android.util.Log
import com.mashup.dorabangs.data.model.DoraError
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class DoraInterceptor @Inject constructor(
    private val json: Json,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            val response = chain.proceed(chain.request())
            val responseBody = response.body
            if (responseBody != null && response.isSuccessful) {
                return response.newBuilder()
                    .body(JSONObject(responseBody.string())["data"].toString().toResponseBody())
                    .build()
            }
            responseBody?.let { body ->
                val errorBody = JSONObject(body.string())["error"]
                val decodeErrorBody = json.decodeFromString<DoraError>(errorBody.toString())
                decodeErrorBody.let { doraError ->
                    throw DoraException(
                        code = doraError.code,
                        message = doraError.message,
                    )
                }
            }
            return response
        } catch (e: Exception) {
            Log.d(TAG, "exception:$e ")
            throw e
        }
    }
}

class DoraException(
    val code: String,
    override val message: String?,
) : IOException(message)
