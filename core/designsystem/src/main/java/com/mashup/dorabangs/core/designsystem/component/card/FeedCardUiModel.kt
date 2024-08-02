package com.mashup.dorabangs.core.designsystem.component.card

import java.time.Instant
import java.time.temporal.ChronoUnit

data class FeedCardUiModel(
    val postId: String,
    val folderId: String,
    val title: String?,
    val content: String?,
    val category: String? = "",
    val createdAt: String?,
    val keywordList: List<String>?,
    val thumbnail: String?,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
) {
    companion object {
        fun String?.convertCreatedDate(): String {
            this?.let {
                val givenDate = Instant.parse(this)
                val currentDate = Instant.now()
                val dayDiff = ChronoUnit.DAYS.between(givenDate, currentDate)
                return if (dayDiff == 0L) "오늘" else "${dayDiff}일 전"
            }
            return "없음"
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
