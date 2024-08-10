package com.mashup.dorabangs.domain.usecase.posts

import com.mashup.dorabangs.domain.repository.PostsRepository
import javax.inject.Inject

class GetPostsPageUseCase @Inject constructor(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke(
        page: Int,
        order: String? = null,
        favorite: Boolean? = null,
        isRead: Boolean? = null,
    ) = postsRepository.getPostsPage(
        page = page,
        order = order,
        favorite = favorite,
        isRead = isRead
    )
}