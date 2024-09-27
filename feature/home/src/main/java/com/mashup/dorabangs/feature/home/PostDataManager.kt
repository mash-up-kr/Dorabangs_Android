package com.mashup.dorabangs.feature.home

import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel.FeedCardUiModel
import com.mashup.dorabangs.feature.model.PagingInfo
import java.util.concurrent.ConcurrentHashMap

class PostDataManager {
    private val pagingInfoCache = ConcurrentHashMap<String, PagingInfo>()
    private val postIdCache = ConcurrentHashMap<String, List<String>>()
    private val postDataCache = ConcurrentHashMap<String, FeedCardUiModel>()

    fun getCachedPostData(key: String) =
        postIdCache[key]
            ?.mapNotNull { postId -> postDataCache[postId] }

    fun getPostIdList(key: String) = postIdCache[key].orEmpty()

    fun getPost(postId: String) = postDataCache[postId]

    fun getHasNext(key: String) = pagingInfoCache[key]?.hasNext ?: false

    fun updatePostCache(post: FeedCardUiModel) {
        updatePostCache(post.postId, post)
    }

    fun updatePostCache(postId: String, post: FeedCardUiModel) {
        postDataCache[postId] = post
    }

    fun updatePostIdCache(key: String, idList: List<String>) {
        postIdCache[key] = idList
    }

    fun setMustRefreshData(key: String) {
        postIdCache.remove(key)
        pagingInfoCache.remove(key)
    }

    fun getPagingInfo(key: String): PagingInfo {
        return pagingInfoCache[key] ?: PagingInfo.getDefault(key).apply {
            pagingInfoCache[key] = this
        }
    }

    fun updateNextPagingInfo(key: String, pagingInfo: PagingInfo, hasNext: Boolean) {
        pagingInfoCache[key] = pagingInfo.copy(
            nextPage = pagingInfo.nextPage + 1,
            hasNext = hasNext,
        )
    }

    fun cacheFeedCardList(key: String, data: List<FeedCardUiModel>) {
        data.forEach { post ->
            postDataCache[post.postId] = post
        }
        postIdCache[key] = (postIdCache[key] ?: emptyList()) + data.map { it.postId }
    }
}
