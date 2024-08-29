package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.repository.UserRepository
import javax.inject.Inject

class SetUserAccessTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(accessToken: String) {
        userRepository.setUserAccessToken(accessToken = accessToken)
    }
}
