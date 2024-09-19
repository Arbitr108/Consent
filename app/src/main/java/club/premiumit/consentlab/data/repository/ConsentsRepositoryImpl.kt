package club.premiumit.consentlab.data.repository

import club.premiumit.consentlab.domain.repository.ConsentsRepository

// in real case the dispatcher will not be needed as
// all mainstream storage libraries provide main safe contract
/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
class ConsentsRepositoryImpl : ConsentsRepository {
    // it would be android db or shared preferences storage
    // in real case
    private val _storage = mutableListOf<String>()

    override suspend fun saveGranted(granted: List<String>) {
        _storage.clear()
        _storage.addAll(granted)
    }

    override suspend fun getGranted(): List<String> = _storage.toList()
}