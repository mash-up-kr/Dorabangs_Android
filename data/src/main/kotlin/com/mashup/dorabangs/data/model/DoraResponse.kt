package com.mashup.dorabangs.data.model

import kotlinx.serialization.Serializable
import java.lang.Exception

@Serializable
data class DoraResponse<T : Any> (
    private var data: T?,
    val success: Boolean = false,
    val error: DoraError? = null,
) {

    fun getData(): T? {
        if (!success && error != null) {
            throw Exception(error.message)
        }
        return data
    }
}

@Serializable
data class DoraError(
    val code: String = "UnKnown",
    val message: String = "",
)