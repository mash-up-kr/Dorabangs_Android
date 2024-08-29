package save

import com.mashup.dorabangs.domain.repository.save.DoraUrlCheckRepository
import com.mashup.dorabangs.domain.usecase.save.DoraSaveLinkDataModel
import com.mashup.dorabangs.domain.usecase.save.DoraUrlCheckUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DoraUrlCheckUseCaseTest {
    private val repository: DoraUrlCheckRepository = mockk()
    private val useCase = spyk(DoraUrlCheckUseCase(repository))

    @Test
    fun `url이 제대로 들어갔을 때 테스트`() = runTest {
        val mockData = DoraSaveLinkDataModel(
            urlLink = "https://www.naver.com",
            title = "네이버 메인",
            thumbnailUrl = "https://www.naver.com/대충_네이버_썸네일이라 치셈",
            isShortLink = false,
        )
        coEvery { repository.checkUrl(urlLink = "https://www.naver.com") } returns mockData

        val result = useCase.invoke(urlLink = "https://www.naver.com")

        assert(result == mockData)
        coVerify(exactly = 1) { useCase.invoke(urlLink = "https://www.naver.com") }
    }
}
