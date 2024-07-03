package usecase.user

import repository.UserDataStoreRepository
import javax.inject.Inject

class SetIsFirstEntryUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
) {
    suspend operator fun invoke(isFirst: Boolean) {
        userDataStoreRepository.setIsFirstEntry(isFirst = isFirst)
    }
}
