package com.mashup.dorabangs.feature.home

import com.mashup.dorabangs.core.designsystem.component.chips.FeedUiModel.FeedCardUiModel
import com.mashup.dorabangs.domain.model.Folder
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.model.Posts
import com.mashup.dorabangs.feature.model.PagingInfo
import com.mashup.dorabangs.feature.model.toUiModel
import java.util.concurrent.ConcurrentHashMap

class PostDataManager(
    private val getPost: suspend (String, PagingInfo) -> Posts,
) {
    private val pagingInfoCache = ConcurrentHashMap<String, PagingInfo>()
    private val postIdCache = ConcurrentHashMap<String, List<String>>()
    private val postDataCache = ConcurrentHashMap<String, FeedCardUiModel>()

    suspend fun fetchPostData(key: String, folderList: List<Folder>): List<FeedCardUiModel> {
        return if (postIdCache[key].isNullOrEmpty()) {
            fetchRemotePostData(key, folderList)
        } else {
            postIdCache[key]
                ?.mapNotNull { postId -> postDataCache[postId] }
                .orEmpty()
        }
    }

    suspend fun fetchRemotePostData(key: String, folderList: List<Folder>): List<FeedCardUiModel> {
        val pagingInfo = getPagingInfo(key)
        return getPost(key, pagingInfo)
            .updateNextPagingInfo(key)
            .items
            .toUIModel(folderList)
            .caching(key)
    }

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

    private fun getPagingInfo(key: String): PagingInfo {
        return pagingInfoCache[key] ?: PagingInfo.getDefault(key).apply {
            pagingInfoCache[key] = this
        }
    }

    private fun Posts.updateNextPagingInfo(key: String): Posts {
        val pagingInfo = getPagingInfo(key)
        pagingInfoCache[key] = pagingInfo.copy(
            nextPage = pagingInfo.nextPage + 1,
            hasNext = this.metaData.hasNext,
        )
        return this
    }

    private fun List<FeedCardUiModel>.caching(key: String): List<FeedCardUiModel> {
        this.forEach { post ->
            postDataCache[post.postId] = post
        }
        postIdCache[key] = (postIdCache[key] ?: emptyList()) + this.map { it.postId }
        return this
    }

    private fun List<Post>.toUIModel(folderList: List<Folder>) = this.map { post ->
        val category = folderList.firstOrNull { folder -> folder.id == post.folderId }?.name.orEmpty()
        post.toUiModel(category)
    }
}
