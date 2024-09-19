package club.premiumit.consentlab.domain.repository

import club.premiumit.consentlab.domain.model.DataType

/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
interface CostsRepository{
    suspend fun getCosts(): List<DataType>
}