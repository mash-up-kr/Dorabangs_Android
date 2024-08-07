package com.mashup.dorabangs.domain.usecase.posts

import com.mashup.dorabangs.domain.model.Link
import com.mashup.dorabangs.domain.repository.PostsRepository
import com.mashup.dorabangs.domain.usecase.user.SetNeedToUpdateDataUseCase
import javax.inject.Inject

class SaveLinkUseCase @Inject constructor(
    private val postsRepository: PostsRepository,
    private val setNeedToUpdateDataUseCase: SetNeedToUpdateDataUseCase,
) {

    suspend operator fun invoke(link: Link, fetchUpdate: Boolean = false) {
        setNeedToUpdateDataUseCase(needToUpdate = fetchUpdate)
        postsRepository.saveLink(link)
    }
}
