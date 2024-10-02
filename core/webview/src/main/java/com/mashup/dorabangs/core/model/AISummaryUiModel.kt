package com.mashup.dorabangs.core.model

import java.io.Serializable

@kotlinx.serialization.Serializable
data class AISummaryUiModel(
    val description: String? = "",
    val keywords: List<String?>? = listOf(),
    val url: String? = "",
) : Serializable
