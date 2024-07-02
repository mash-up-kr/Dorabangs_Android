import io.mockk.spyk
import org.junit.Test
import usecase.SampleUseCase

class SampleUseCaseTest {
    private val test: SampleUseCase = spyk(SampleUseCase())

    @Test
    fun test() {
        test.baboTest(true)
    }
}
