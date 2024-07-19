package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.repository.UserRepository
import javax.inject.Inject

class SetLastCopiedUrlUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(url: String) {
        return userRepository.setLastCopiedUrl(url = url)
    }
}
