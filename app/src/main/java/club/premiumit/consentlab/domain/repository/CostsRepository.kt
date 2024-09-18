package club.premiumit.consentlab.domain.repository

import club.premiumit.consentlab.domain.model.DataType

interface CostsRepository{
    suspend fun getCosts(): List<DataType>
}