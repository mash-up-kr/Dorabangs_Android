package com.mashup.dorabangs.domain.usecase.user

import com.mashup.dorabangs.domain.repository.UserDataStoreRepository
import javax.inject.Inject

class SetIsFirstEntryUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
) {
    suspend operator fun invoke(isFirst: Boolean) {
        userDataStoreRepository.setIsFirstEntry(isFirst = isFirst)
    }
}
