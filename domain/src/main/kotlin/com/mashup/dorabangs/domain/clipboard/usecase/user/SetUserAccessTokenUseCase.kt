package com.mashup.dorabangs.domain.clipboard.usecase.user

import com.mashup.dorabangs.domain.clipboard.repository.UserDataStoreRepository
import javax.inject.Inject

class SetUserAccessTokenUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
) {
    suspend operator fun invoke(accessToken: String) {
        userDataStoreRepository.setUserAccessToken(accessToken = accessToken)
    }
}
