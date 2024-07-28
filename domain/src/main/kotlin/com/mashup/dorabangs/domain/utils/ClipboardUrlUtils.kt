package com.mashup.dorabangs.domain.utils

fun String.isValidUrl(): Boolean {
    val urlPattern =
        "^(https?://)?([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})(:[0-9]{1,5})?(/.*)?\$".toRegex()
    return this.matches(urlPattern)
}
