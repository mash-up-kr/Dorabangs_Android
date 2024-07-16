package com.mashup.dorabangs.domain.usecase.posts

import com.mashup.dorabangs.domain.model.Posts
import com.mashup.dorabangs.domain.repository.PostsRepository
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke(
        page: Int? = null,
        limit: Int? = null,
        order: String? = null,
        favorite: Boolean? = null,
    ): Posts = postsRepository.getPosts(
        page = page,
        limit = limit,
        order = order,
        favorite = favorite,
    )
}