package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserAccessTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<String> {
        return userRepository.getUserAccessToken()
    }
}
