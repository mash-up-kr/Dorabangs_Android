package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetNeedToUpdateDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Boolean {
        return userRepository.getNeedToUpdateData().first()
    }
}
