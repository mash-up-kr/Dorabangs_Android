package usecase.user

import kotlinx.coroutines.flow.Flow
import repository.UserDataStoreRepository
import javax.inject.Inject

class GetUserAccessTokenUseCase @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
) {
    operator fun invoke(): Flow<String> {
        return userDataStoreRepository.getUserAccessToken()
    }
}
