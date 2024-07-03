package com.mashup.dorabangs.domain.clipboard.usecase.user

import com.mashup.dorabangs.domain.clipboard.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserAccessTokenUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
) {
    operator fun invoke(): Flow<String> {
        return userDataStoreRepository.getUserAccessToken()
    }
}
