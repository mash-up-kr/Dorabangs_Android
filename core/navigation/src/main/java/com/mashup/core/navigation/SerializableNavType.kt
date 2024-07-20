package com.mashup.core.navigation

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json
import java.io.Serializable

/**
 * compose-navigation serializable type
 */
inline fun <reified T : Serializable> serializableNavType(
    isNullableAllowed: Boolean = false,
): NavType<T> {
    return object : NavType<T>(isNullableAllowed) {
        override val name: String
            get() = "SupportSerializable"

        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putSerializable(key, value)
        }

        override fun get(bundle: Bundle, key: String): T? {
            return bundle.bundleSerializable(key) as? T
        }

        override fun parseValue(value: String): T {
            return Json.decodeFromString(value)
        }
    }
}

inline fun <reified T : Serializable> Bundle?.bundleSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this?.getSerializable(key, T::class.java)
    } else {
        this?.getSerializable(key) as? T
    }
}
