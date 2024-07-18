package com.mashup.dorabangs.core.designsystem.component.card

data class FeedCardUiModel(
    val title: String,
    val content: String,
    val category: String = "",
    val createdAt: String,
    val keywordList: List<String>,
    val thumbnail: Int, // TODO - url String 변경 필요
    val isLoading: Boolean = false,
) {
    companion object {
        fun getDefaultFeedCard(): List<FeedCardUiModel> {
            return listOf(
                FeedCardUiModel(
                    title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    keywordList = listOf("다연", "호현", "석주"),
                    category = "디자인",
                    createdAt = "1",
                    thumbnail = androidx.core.R.drawable.ic_call_answer,
                ),
                FeedCardUiModel(
                    title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    keywordList = listOf("다연", "호현", "석주"),
                    category = "디자인",
                    createdAt = "1",
                    thumbnail = androidx.core.R.drawable.ic_call_answer,
                ),
                FeedCardUiModel(
                    title = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    content = "실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기실험 0건인 조직에서, 가장 실험을 활발하게 하는 조직 되기",
                    keywordList = listOf("다연", "호현", "석주"),
                    category = "디자인",
                    createdAt = "1",
                    thumbnail = androidx.core.R.drawable.ic_call_answer,
                ),
            )
        }
    }
}
