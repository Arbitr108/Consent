package club.premiumit.consentlab.data.repository

import club.premiumit.consentlab.data.models.toDomain
import club.premiumit.consentlab.data.source.RemoteCostsService
import club.premiumit.consentlab.domain.model.CostRule
import club.premiumit.consentlab.domain.repository.RulesRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// For the offline use we need to cache the rules in the db
/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
class RulesRepositoryImpl(
    private val remoteCostsService: RemoteCostsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RulesRepository {
    override suspend fun getRules(): List<CostRule> =
        withContext(dispatcher) {
            try {
                remoteCostsService.getCostsRules().map {
                    it.toDomain()
                }
            } catch (e: CancellationException) {
                // Structured concurrency friendly try catch
                throw e
            } catch (e: Throwable) {
                // do what is required here
                listOf()
            }
        }
}