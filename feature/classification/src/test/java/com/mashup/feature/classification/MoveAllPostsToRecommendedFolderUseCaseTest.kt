package com.mashup.feature.classification

import com.mashup.dorabangs.domain.model.DoraSampleResponse
import com.mashup.dorabangs.domain.repository.AIClassificationRepository
import com.mashup.dorabangs.domain.usecase.aiclassification.MoveAllPostsToRecommendedFolderUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MoveAllPostsToRecommendedFolderUseCaseTest {

    private val repository: AIClassificationRepository = mockk()
    private val useCase = spyk(MoveAllPostsToRecommendedFolderUseCase(repository))

    @Test
    fun `moveAllPost 성공 테스트`() = runTest {
        val suggestionId = "abcde"
        val expected = DoraSampleResponse(isSuccess = true)
        coEvery {
            repository.moveAllPostsToRecommendedFolder(suggestionId)
        } returns expected

        val result = useCase.invoke(suggestionId)

        assert(result.isSuccess)
        coVerify(exactly = 1) { repository.moveAllPostsToRecommendedFolder(eq(suggestionId)) }
    }

    @Test
    fun `moveAllPost 실패 테스트`() = runTest {
        val suggestionId = "abcde"
        val expected = DoraSampleResponse(isSuccess = false, errorMsg = "실패요")
        coEvery {
            repository.moveAllPostsToRecommendedFolder(suggestionId)
        } returns expected

        val result = useCase.invoke(suggestionId)

        assert(result == expected)
        assert(result.errorMsg == "실패요")
        coVerify(exactly = 1) { repository.moveAllPostsToRecommendedFolder(eq(suggestionId)) }
    }
}
