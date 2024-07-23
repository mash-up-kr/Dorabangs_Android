package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.repository.UserRepository
import javax.inject.Inject

class SetIdLinkToReadLaterUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(id: String) {
        return userRepository.setIdLinkToReadLater(id = id)
    }
}
