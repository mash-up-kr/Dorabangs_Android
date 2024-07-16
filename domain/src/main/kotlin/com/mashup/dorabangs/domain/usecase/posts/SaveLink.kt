package com.mashup.dorabangs.domain.usecase.posts

import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.repository.PostsRepository
import javax.inject.Inject

class SaveLink @Inject constructor(
    private val postsRepository: PostsRepository,
) {

    suspend operator fun invoke(link: Link) =
        postsRepository.saveLink(link)
}