package club.premiumit.consentlab.domain.usecase

import club.premiumit.consentlab.domain.model.DataType
import club.premiumit.consentlab.domain.repository.CostsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateUseCaseTest {


    private val repository: CostsRepository = mockk()

    @Test
    fun `test the usecase calculates costs total correctly`() = runTest {
        val costs = listOf(
            DataType("testType1", 10),
            DataType("testType2", 20),
            DataType("testType3", 30),
            DataType("testType4", 40),
            DataType("testType5", 50),
            DataType("testType6", 60)
        )

        val types = listOf("testType3", "testType5", "testType1")
        coEvery { repository.getCosts() } returns costs

        val result = CalculateUseCaseImpl(repository)(types)
        assertEquals(90, result) // 30 + 50 + 10
    }
}