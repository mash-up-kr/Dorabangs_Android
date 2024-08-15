package com.mashup.dorabangs.feature.utils

import com.mashup.dorabangs.feature.home.HomeState

fun getCacheKey(state: HomeState) =
    when (state.selectedIndex) {
        0 -> "all"
        1 -> "favorite"
        else -> state.tapElements[state.selectedIndex].id
    }

fun getCacheKey(state: HomeState, index: Int) =
    when (state.selectedIndex) {
        0 -> "all"
        1 -> "favorite"
        else -> state.tapElements[index].id
    }
