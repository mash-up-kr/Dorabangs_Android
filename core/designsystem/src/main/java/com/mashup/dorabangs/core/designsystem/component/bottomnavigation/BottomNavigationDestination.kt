package com.mashup.dorabangs.core.designsystem.component.bottomnavigation

import com.mashup.dorabangs.core.designsystem.R

enum class BottomNavigationDestination(
    val icon: Int,
    val routeName: Int,
) {
    HOME(
        icon = androidx.core.R.drawable.ic_call_answer,
        routeName = R.string.home,
    ),
    STORAGE(
        icon = androidx.core.R.drawable.ic_call_answer,
        routeName = R.string.storage,
    ),
}
