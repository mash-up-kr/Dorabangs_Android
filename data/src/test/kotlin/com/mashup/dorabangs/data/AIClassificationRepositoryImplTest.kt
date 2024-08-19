package com.mashup.dorabangs.data

import com.mashup.dorabangs.data.datasource.remote.api.AIClassificationRemoteDataSource
import com.mashup.dorabangs.data.repository.AIClassificationRepositoryImpl
import com.mashup.dorabangs.domain.model.DoraSampleResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AIClassificationRepositoryImplTest {

    private val dataSource = mockk<AIClassificationRemoteDataSource>()
    private val repository = spyk(AIClassificationRepositoryImpl(dataSource))

    @Test
    fun `성공 테스트`() = runTest {
        val suggestionId = "abcde"
        val expected = DoraSampleResponse(isSuccess = true)
        coEvery { dataSource.moveAllPostsToRecommendedFolder(suggestionId) } returns Unit

        val result = repository.moveAllPostsToRecommendedFolder(suggestionId)

        assert(result == expected)
        coVerify(exactly = 1) { dataSource.moveAllPostsToRecommendedFolder(eq(suggestionId)) }
    }

    @Test
    fun `실패 테스트-1`() = runTest {
        val suggestionId = "abcde"
        val expected = DoraSampleResponse(isSuccess = false, errorMsg = "에러임")
        coEvery { dataSource.moveAllPostsToRecommendedFolder(suggestionId) } throws IllegalStateException(
            "에러임"
        )

        val result = repository.moveAllPostsToRecommendedFolder(suggestionId)

        assert(result == expected)
        assert(result.isSuccess.not())
        assert(result.errorMsg == "에러임")
        coVerify(exactly = 1) { dataSource.moveAllPostsToRecommendedFolder(eq(suggestionId)) }
    }

    @Test
    fun `실패 테스트-2`() = runTest {
        val suggestionId = "abcde"
        val expected = DoraSampleResponse(isSuccess = false)
        coEvery { dataSource.moveAllPostsToRecommendedFolder(suggestionId) } throws NullPointerException()

        val result = repository.moveAllPostsToRecommendedFolder(suggestionId)

        assert(result == expected)
        assert(result.isSuccess.not())
        assert(result.errorMsg == "")
        coVerify(exactly = 1) { dataSource.moveAllPostsToRecommendedFolder(eq(suggestionId)) }
    }
}