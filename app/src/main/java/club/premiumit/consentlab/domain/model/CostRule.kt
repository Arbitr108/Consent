package club.premiumit.consentlab.domain.model

import club.premiumit.consentlab.core.percentOf
import kotlin.math.abs

sealed interface CostRule {
    val priority: Int
    val costChangeRule: Int
    fun isApplicable(target: List<String>): Boolean

    fun apply(cost: Int): Double =
        if (costChangeRule > 0)
            costChangeRule percentOf cost
        else
            -(abs(costChangeRule) percentOf cost)

    data class CostStatic(
        val dataTypes: List<String>,
        override val costChangeRule: Int,
        override val priority: Int,
    ) : CostRule {
        override fun isApplicable(target: List<String>): Boolean = target.containsAll(dataTypes)
    }

    sealed interface CostDynamic : CostRule {
        val number: Int
        override val costChangeRule: Int

        data class LessOrEquals(
            override val number: Int,
            override val costChangeRule: Int,
            override val priority: Int,
        ) : CostDynamic {
            override fun isApplicable(target: List<String>): Boolean = target.size <= number
        }

        data class MoreOrEquals(
            override val number: Int,
            override val costChangeRule: Int,
            override val priority: Int,
        ) : CostDynamic {

            override fun isApplicable(target: List<String>): Boolean = target.size >= number

        }

        data class Equals(
            override val number: Int,
            override val costChangeRule: Int,
            override val priority: Int,
        ) : CostDynamic {
            override fun isApplicable(target: List<String>): Boolean = target.size == number
        }
    }
}