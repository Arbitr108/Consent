package club.premiumit.consentlab.domain.repository

import club.premiumit.consentlab.domain.model.CostRule

/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
interface RulesRepository {
    suspend fun getRules(): List<CostRule>
}