package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.model.DeviceToken
import com.mashup.dorabangs.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(deviceToken: DeviceToken): String {
        return userRepository.registerDeviceToken(deviceToken)
    }
}
