package com.mashup.dorabangs.domain.clipboard.usecase.user

import com.mashup.dorabangs.domain.clipboard.repository.UserDataStoreRepository
import javax.inject.Inject

class SetIsFirstEntryUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
) {
    suspend operator fun invoke(isFirst: Boolean) {
        userDataStoreRepository.setIsFirstEntry(isFirst = isFirst)
    }
}
