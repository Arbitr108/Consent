package club.premiumit.consentlab.domain.usecase

import club.premiumit.consentlab.core.percentOf
import club.premiumit.consentlab.domain.model.CostRule
import club.premiumit.consentlab.domain.model.DataType
import club.premiumit.consentlab.domain.repository.RulesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class CostAdjustUseCaseTest {
    private val repository: RulesRepository = mockk()

    @Test
    fun `test the usecase calculates static adjustments correctly`() = runTest {
        //"Banking snoppy" or "Why do you care" test cases
        val rules = listOf(
            CostRule.CostStatic(
                dataTypes = listOf(
                    DataType("testType4", 40).name,
                    DataType("testType5", 50).name,
                ), costChangeRule = 15, priority = 1
            ),
        )
        coEvery { repository.getRules() } returns rules

        val declared = listOf(
            DataType("testType1", 10),
            DataType("testType2", 20),
            DataType("testType3", 30),
            DataType("testType4", 40),
            DataType("testType5", 50),
            DataType("testType6", 60)
        )

        val names = declared.map { it.name }
        val total = declared.sumOf { it.value }

        val result = CostAdjustUseCaseImpl(repository)(names, total)


        assertEquals(
            actual = result, expected = ((10 + 20 + 30 + 40 + 50 + 60) +
                    (15 percentOf (10 + 20 + 30 + 40 + 50 + 60)))
        )
    }

    @Test
    fun `test the usecase calculates dynamic adjustments correctly`() = runTest {
        // "Good citizen" test case
        val rules = listOf(
            CostRule.CostDynamic.LessOrEquals(
                number = 4, costChangeRule = -10, priority = 1
            ),
        )
        coEvery { repository.getRules() } returns rules

        val declared = listOf(
            DataType("testType1", 10),
            DataType("testType2", 20),
            DataType("testType3", 30),
            DataType("testType4", 40),
        )

        val names = declared.map { it.name }
        val total = declared.sumOf { it.value }

        val result = CostAdjustUseCaseImpl(repository)(names, total)


        assertEquals(
            actual = result, expected = ((10 + 20 + 30 + 40) -
                    (10 percentOf (10 + 20 + 30 + 40)))
        )
    }

    @Test
    fun `test the usecase calculates dynamic and static adjustments correctly`() = runTest {
        val rules = listOf(
            // rule 1
            CostRule.CostDynamic.LessOrEquals(
                number = 4, costChangeRule = -10, priority = 1
            ),
            // rule 2
            CostRule.CostStatic(
                dataTypes = listOf(
                    DataType("testType4", 40).name,
                    DataType("testType3", 30).name,
                ), costChangeRule = 27, priority = 2
            ),
            // rule 3
            CostRule.CostStatic(
                dataTypes = listOf(
                    DataType("testType1", 10).name,
                    DataType("testType3", 30).name,
                ), costChangeRule = 5, priority = 3
            ),
        )
        coEvery { repository.getRules() } returns rules

        val declared = listOf(
            DataType("testType1", 10),
            DataType("testType2", 20),
            DataType("testType3", 30),
            DataType("testType4", 40),
        )

        val names = declared.map { it.name }
        val total = declared.sumOf { it.value }

        val result = CostAdjustUseCaseImpl(repository)(names, total)

        assertEquals(
            actual = result,
            expected = ((10 + 20 + 30 + 40)
                    - (10 percentOf (10 + 20 + 30 + 40)) // rule 1
                    + (27 percentOf (10 + 20 + 30 + 40)) // rule 2
                    + (5 percentOf (10 + 20 + 30 + 40))) // rule 3
        )
    }
}