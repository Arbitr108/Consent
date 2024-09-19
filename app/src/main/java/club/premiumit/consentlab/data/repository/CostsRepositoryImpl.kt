package club.premiumit.consentlab.data.repository

import club.premiumit.consentlab.data.models.toDomain
import club.premiumit.consentlab.data.source.RemoteCostsService
import club.premiumit.consentlab.domain.model.DataType
import club.premiumit.consentlab.domain.repository.CostsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// For the offline use we need to cache the costs in the db
/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
class CostsRepositoryImpl(
    private val remoteCostsService: RemoteCostsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CostsRepository {
    override suspend fun getCosts(): List<DataType> =
        withContext(dispatcher){
            remoteCostsService.getCosts().map { it.toDomain() }
        }
}