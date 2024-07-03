package usecase.user

import kotlinx.coroutines.flow.Flow
import repository.UserDataStoreRepository
import javax.inject.Inject

class GetIsFirstEntryUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
) {
    operator fun invoke(): Flow<Boolean> {
        return userDataStoreRepository.getIsFirstEntry()
    }
}
