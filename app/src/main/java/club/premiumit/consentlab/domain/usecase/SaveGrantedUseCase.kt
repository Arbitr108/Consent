package club.premiumit.consentlab.domain.usecase

import club.premiumit.consentlab.domain.repository.ConsentsRepository

// I could avoid this usecase,
// if service.dataCollectedList would contain only selected options )
/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
fun interface SaveGrantedUseCase{
    suspend operator fun invoke(granted: List<String>)
}

class SaveGrantedUseCaseImpl(
    private val repository: ConsentsRepository
) : SaveGrantedUseCase {
    override suspend fun invoke(granted: List<String>) {
        repository.saveGranted(granted)
    }
}