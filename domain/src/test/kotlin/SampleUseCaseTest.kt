import io.mockk.spyk
import org.junit.Test

class SampleUseCaseTest {
    private val test: SampleUseCase = spyk(SampleUseCase())

    @Test
    fun test() {
        test.baboTest(true)
    }

    @Test
    fun test2() {
        test.baboTest(false)
    }
}