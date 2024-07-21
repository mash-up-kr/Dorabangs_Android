package com.mashup.dorabangs.core.designsystem.component.bottomnavigation

import com.mashup.dorabangs.core.designsystem.R

enum class BottomNavigationDestination(
    val icon: Int,
    val routeName: Int,
) {
    HOME(
        icon = R.drawable.ic_home_default,
        routeName = R.string.home,
    ),
    STORAGE(
        icon = R.drawable.ic_folder_default,
        routeName = R.string.storage,
    ),
}
