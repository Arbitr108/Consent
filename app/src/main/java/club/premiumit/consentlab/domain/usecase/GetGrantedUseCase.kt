package club.premiumit.consentlab.domain.usecase

import club.premiumit.consentlab.domain.repository.ConsentsRepository

fun interface GetGrantedUseCase{
    suspend operator fun invoke(): List<String>
}

class GetGrantedUseCaseImpl(
    private val repository: ConsentsRepository
) : GetGrantedUseCase {
    override suspend fun invoke(): List<String> = repository.getGranted()
}