package club.premiumit.consentlab.data.models

import club.premiumit.consentlab.domain.model.CostRule

/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
sealed interface CostRuleRemote {
    val priority: Int
    data class CostStatic(
        val dataTypes: List<String>,
        val costChangeRule: Int,
        override val priority: Int,
    ) : CostRuleRemote


    sealed class CostDynamic : CostRuleRemote {

        data class LessOrEquals(
            val number: Int,
            val costChangeRule: Int,
            override val priority: Int,
        ) : CostDynamic()

        data class MoreOrEquals(
            val number: Int,
            val costChangeRule: Int,
            override val priority: Int,
        ): CostDynamic()

        data class Equals(
            val number: Int,
            val costChangeRule: Int,
            override val priority: Int,
        ): CostDynamic()
    }

}

fun CostRuleRemote.toDomain() =
    when(this){
        is CostRuleRemote.CostStatic -> CostRule.CostStatic(dataTypes, costChangeRule, priority)
        is CostRuleRemote.CostDynamic.Equals -> CostRule.CostDynamic.Equals(number, costChangeRule, priority)
        is CostRuleRemote.CostDynamic.LessOrEquals -> CostRule.CostDynamic.LessOrEquals(number, costChangeRule, priority)
        is CostRuleRemote.CostDynamic.MoreOrEquals -> CostRule.CostDynamic.MoreOrEquals(number, costChangeRule, priority)
    }


