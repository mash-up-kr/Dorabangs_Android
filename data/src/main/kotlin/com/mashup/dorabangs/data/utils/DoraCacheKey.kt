package com.mashup.dorabangs.data.utils

fun doraConvertKey(page: Int, cachedData: String): String {
    return "$page|$cachedData"
}
