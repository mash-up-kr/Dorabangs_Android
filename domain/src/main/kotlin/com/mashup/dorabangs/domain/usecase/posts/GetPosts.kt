package com.mashup.dorabangs.domain.usecase.posts

import androidx.paging.PagingData
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val postsRepository: PostsRepository,
) {
    suspend operator fun invoke(
        needFetchUpdate: Boolean = false,
        order: String? = null,
        favorite: Boolean? = null,
        isRead: Boolean? = null,
        totalCount: (Int) -> Unit = {},
    ): Flow<PagingData<Post>> = postsRepository.getPosts(
        needFetchUpdate = needFetchUpdate,
        order = order?.lowercase(),
        favorite = favorite,
        isRead = isRead,
        totalCount = totalCount,
    )
}
