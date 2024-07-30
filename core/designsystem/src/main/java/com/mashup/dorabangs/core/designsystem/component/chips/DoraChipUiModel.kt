package com.mashup.dorabangs.core.designsystem.component.chips

import androidx.annotation.DrawableRes

data class DoraChipUiModel(
    val id: String = "",
    val title: String = "",
    val postCount: Int = 0,
    @DrawableRes val icon: Int? = null,
)
