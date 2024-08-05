package com.mashup.dorabangs.core.designsystem.component.chips

import androidx.annotation.DrawableRes
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

sealed interface FeedUiModel {
    val uuid: String

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
        val url: String = "",
    ) : FeedUiModel {
        override val uuid: String = UUID.randomUUID().toString()
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

    data class DoraChipUiModel(
        val id: String = "",
        val mergedTitle: String = "",
        val title: String = "",
        val postCount: Int = 0,
        @DrawableRes val icon: Int? = null,
    ) : FeedUiModel {
        override val uuid: String = UUID.randomUUID().toString()
    }
}
