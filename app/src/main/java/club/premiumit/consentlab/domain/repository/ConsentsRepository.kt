package club.premiumit.consentlab.domain.repository

/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
interface ConsentsRepository {
    suspend fun saveGranted(granted: List<String>)
    suspend fun getGranted(): List<String>
}