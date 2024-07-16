package com.mashup.dorabangs.domain.usecase.posts

import com.mashup.dorabangs.domain.repository.PostsRepository
import javax.inject.Inject

class ChangePostFolder @Inject constructor(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke(
        postId: String,
        folderId: String,
    ) = postsRepository.changePostFolder(postId, folderId)
}