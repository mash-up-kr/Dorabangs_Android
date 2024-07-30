package com.mashup.dorabangs.data.model.classification

import com.mashup.dorabangs.data.model.PagingMetaDataResponseModel
import com.mashup.dorabangs.data.model.toDomain
import com.mashup.dorabangs.data.model.toPagingMetaDomain
import com.mashup.dorabangs.domain.model.PageData
import com.mashup.dorabangs.domain.model.PagingInfo
import com.mashup.dorabangs.domain.model.classification.AIClassificationFeedPost
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * TODO AIClassificationPostsResponseModel랑 네이밍 바꿀 것
 * 한번에 마이그레이션하니 diff가 너무 많아서 순차적으로 변경중
 */
@Serializable
data class AIClassificationAIPostListResponseModel(
    @SerialName("list")
    val list: List<AIPostCard?>? = null,
    @SerialName("metadata")
    val metadata: PagingMetaDataResponseModel? = null,
) {
    @Serializable
    data class AIPostCard(
        @SerialName("aiStatus")
        val aiStatus: String? = null,
        @SerialName("createdAt")
        val createdAt: String? = null,
        @SerialName("description")
        val description: String? = null,
        @SerialName("folderId")
        val folderId: String? = null,
        @SerialName("keywords")
        val keywords: List<String?>? = null,
        @SerialName("postId")
        val postId: String? = null,
        @SerialName("readAt")
        val readAt: String? = null,
        @SerialName("thumbnailImgUrl")
        val thumbnailImgUrl: String? = null,
        @SerialName("title")
        val title: String? = null,
        @SerialName("url")
        val url: String? = null,
    )
}

fun List<AIClassificationAIPostListResponseModel.AIPostCard?>?.toDomain(): List<AIClassificationFeedPost> =
    this?.map {
        it?.let { item ->
            AIClassificationFeedPost(
                postId = item.postId.orEmpty(),
                folderId = item.folderId.orEmpty(),
                title = item.title.orEmpty(),
                content = item.description.orEmpty(),
                readAt = item.readAt.orEmpty(),
                createdAt = item.createdAt.orEmpty(),
                aiStatus = item.aiStatus.orEmpty(),
                keywordList = item.keywords?.map { keyword -> keyword.orEmpty() } ?: listOf(),
                thumbnail = item.thumbnailImgUrl.orEmpty(),
                isFavorite = false,
                isLoading = false,
            )
        } ?: error("1차 에러")
    } ?: listOf()

fun AIClassificationAIPostListResponseModel.toPagingDomain(): PageData<List<AIClassificationFeedPost>> {
    return PageData(
        data = list.toDomain(),
        pagingInfo = metadata?.toPagingMetaDomain() ?: PagingInfo(
            hasNext = false,
            total = 0,
        ),
    )
}
