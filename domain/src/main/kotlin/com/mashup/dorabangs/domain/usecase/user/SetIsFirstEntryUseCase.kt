package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.repository.UserRepository
import javax.inject.Inject

class SetIsFirstEntryUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(isFirst: Boolean) {
        userRepository.setIsFirstEntry(isFirst = isFirst)
    }
}
