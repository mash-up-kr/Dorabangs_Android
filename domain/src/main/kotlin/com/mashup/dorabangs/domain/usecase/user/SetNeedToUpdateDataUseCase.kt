package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.repository.UserRepository
import javax.inject.Inject

class SetNeedToUpdateDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(needToUpdate: Boolean) {
        userRepository.setNeedToUpdateData(needToUpdate = needToUpdate)
    }
}
