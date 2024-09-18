package club.premiumit.consentlab.domain.repository

interface ConsentsRepository {
    suspend fun saveGranted(granted: List<String>)
    suspend fun getGranted(): List<String>
}