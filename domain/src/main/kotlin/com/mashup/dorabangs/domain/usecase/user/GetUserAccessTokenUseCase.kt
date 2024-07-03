package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserAccessTokenUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
) {
    operator fun invoke(): Flow<String> {
        return userDataStoreRepository.getUserAccessToken()
    }
}
