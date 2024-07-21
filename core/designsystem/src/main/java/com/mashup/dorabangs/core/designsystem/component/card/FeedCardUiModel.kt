package com.mashup.dorabangs.core.designsystem.component.card

import java.time.Instant
import java.time.temporal.ChronoUnit

data class FeedCardUiModel(
    val id: String,
    val title: String?,
    val content: String?,
    val category: String? = "",
    val createdAt: String?,
    val keywordList: List<String?>?,
    val thumbnail: String?,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
) {
    companion object {
        fun getDefaultFeedCard(): List<FeedCardUiModel> {
            return listOf(
                FeedCardUiModel(
                    id = "",
                    title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    keywordList = listOf("다연", "호현", "석주"),
                    category = "디자인",
                    createdAt = "2024-07-18T15:50:36.181Z",
                    thumbnail = "",
                ),
                FeedCardUiModel(
                    id = "",
                    title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    keywordList = listOf("다연", "호현", "석주"),
                    category = "디자인",
                    createdAt = "2024-07-18T15:50:36.181Z",
                    thumbnail = "",
                ),
                FeedCardUiModel(
                    id = "",
                    title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    keywordList = listOf("다연", "호현", "석주"),
                    category = "디자인",
                    createdAt = "2024-07-18T15:50:36.181Z",
                    thumbnail = "",
                ),
            )
        }

        fun String?.convertCreatedDate(): Long {
            this?.let {
                val givenDate = Instant.parse(this)
                val currentDate = Instant.now()
                val daysBetween = ChronoUnit.DAYS.between(givenDate, currentDate)
                return daysBetween
            }
            return 0L
        }
    }
}
