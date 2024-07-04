package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.model.DeviceToken
import com.mashup.dorabangs.domain.repository.UserRemoteRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRemoteRepository: UserRemoteRepository,
) {

    suspend operator fun invoke(deviceToken: DeviceToken): String {
        return userRemoteRepository.registerUser(deviceToken)
    }
}
