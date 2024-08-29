package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetIdFromLinkToReadLaterUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): String {
        return userRepository.getIdFromLinkToReadLater().firstOrNull().orEmpty()
    }
}
