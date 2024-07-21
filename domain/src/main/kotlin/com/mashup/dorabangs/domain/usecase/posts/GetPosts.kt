package com.mashup.dorabangs.domain.usecase.posts

import androidx.paging.PagingData
import com.mashup.dorabangs.domain.model.Post
import com.mashup.dorabangs.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import java.util.Locale
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke(
        order: String? = null,
        favorite: Boolean? = null,
        isRead: Boolean? = null,
    ): Flow<PagingData<Post>> = postsRepository.getPosts(
        order = order?.lowercase(Locale.ROOT),
        favorite = favorite,
        isRead = isRead,
    )
}
