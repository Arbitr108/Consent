package club.premiumit.consentlab.domain.repository

import club.premiumit.consentlab.domain.model.CostRule

interface RulesRepository {
    suspend fun getRules(): List<CostRule>
}