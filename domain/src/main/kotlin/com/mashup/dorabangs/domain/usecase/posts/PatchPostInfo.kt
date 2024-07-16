package com.mashup.dorabangs.domain.usecase.posts

import com.mashup.dorabangs.domain.model.PostInfo
import com.mashup.dorabangs.domain.repository.PostsRepository
import javax.inject.Inject

class PatchPostInfo @Inject constructor(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke(
        postId: String,
        postInfo: PostInfo,
    ) = postsRepository.patchPostInfo(postId, postInfo)
}
