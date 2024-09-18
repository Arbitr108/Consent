package club.premiumit.consentlab.domain.model

sealed interface CostRule {
    val priority: Int
    val costChangeRule: Int
    fun isApplicable(target: List<String>) : Boolean

    fun apply(cost: Int): Double =
        if(cost > 0)
            maxOf(cost * (1 + costChangeRule / 100.0), 0.0)
        else
            maxOf(cost * (1 - costChangeRule / 100.0), 0.0)

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
