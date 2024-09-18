package club.premiumit.consentlab.data.repository

import club.premiumit.consentlab.data.models.toDomain
import club.premiumit.consentlab.data.source.RemoteCostsService
import club.premiumit.consentlab.domain.model.CostRule
import club.premiumit.consentlab.domain.repository.RulesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// For the offline use we need to cache the rules in the db
class RulesRepositoryImpl(
    private val remoteCostsService: RemoteCostsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RulesRepository {
    override suspend fun getRules(): List<CostRule>  =
        withContext(dispatcher){
            remoteCostsService.getCostsRules().map {
                it.toDomain()
            }
        }
}