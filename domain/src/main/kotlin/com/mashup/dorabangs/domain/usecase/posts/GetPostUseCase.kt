package com.mashup.dorabangs.domain.usecase.posts

import com.mashup.dorabangs.domain.repository.PostsRepository
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke(postId: String) =
        postsRepository.getPost(postId)
}