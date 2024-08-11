package com.mashup.dorabangs.domain.usecase.posts

import com.mashup.dorabangs.domain.repository.PostsRepository
import javax.inject.Inject

class ChangeLocalPostUseCase @Inject constructor(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke(postId: String, isFavorite: Boolean) =
        postsRepository.updateBookMarkState(postId, isFavorite)
}
