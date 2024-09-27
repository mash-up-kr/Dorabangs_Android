package com.mashup.dorabangs.core.designsystem.component.chips

import androidx.annotation.DrawableRes
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.UUID
import com.mashup.dorabangs.core.designsystem.R as CR

sealed interface FeedUiModel {
    val uuid: String

    data class FeedCardUiModel(
        val postId: String = "",
        val folderId: String = "",
        val title: String? = "",
        val content: String? = "",
        val category: String? = "",
        val createdAt: String? = "",
        val keywordList: List<String?>? = listOf(),
        val thumbnail: String? = "",
        val isFavorite: Boolean = false,
        val isLoading: Boolean = false,
        val url: String = "",
        val readAt: String? = null,
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

            fun createCurrentTime(): String? {
                val zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                val utcDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"))
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                return utcDateTime.format(formatter)
            }
        }
    }

    data class DoraChipUiModel(
        val id: String = "",
        val mergedTitle: String = "",
        val title: String = "",
        val postCount: Int = 0,
        val folderId: String = "",
        val isAIGenerated: Boolean = false,
        @DrawableRes val icon: Int? = CR.drawable.ic_3d_all_small,
    ) : FeedUiModel {
        override val uuid: String = UUID.randomUUID().toString()

        companion object {
            fun getDefaultModelList() = listOf(
                DoraChipUiModel(
                    title = "전체",
                    icon = CR.drawable.ic_3d_all_small,
                ),
                DoraChipUiModel(
                    title = "즐겨찾기",
                    icon = CR.drawable.ic_3d_bookmark_small,
                ),
            )
        }
    }
}
