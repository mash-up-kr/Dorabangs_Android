package com.mashup.dorabangs.core.designsystem.component.card

import java.time.Instant
import java.time.temporal.ChronoUnit

data class FeedCardUiModel(
    val postId: String = "",
    val folderId: String = "",
    val title: String? = "",
    val content: String? = "",
    val category: String? = "",
    val createdAt: String? = "",
    val keywordList: List<String>? = listOf(),
    val thumbnail: String? = "",
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
) {
    companion object {
        fun String?.convertCreatedDate(): Long {
            this?.let {
                val givenDate = Instant.parse(this)
                val currentDate = Instant.now()
                return ChronoUnit.DAYS.between(givenDate, currentDate)
            }
            return 0L
        }

        fun String?.convertCreatedSecond(): Int {
            this?.let {
                val givenDate = Instant.parse(this)
                val currentDate = Instant.now()
                return ChronoUnit.SECONDS.between(givenDate, currentDate).toInt()
            }
            return 0
        }
    }
}
