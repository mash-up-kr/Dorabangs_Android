package com.mashup.dorabangs.domain.usecase.posts

import com.mashup.dorabangs.domain.repository.PostsRepository
import javax.inject.Inject

class GetLocalPostsUseCase @Inject constructor(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke(limit: Int) = postsRepository.getLocalPosts(limit)
}
