package com.mashup.dorabangs.core.designsystem.component.chips

import androidx.annotation.DrawableRes

data class DoraChipUiModel(
    val title: String,
    @DrawableRes val icon: Int? = null,
)
