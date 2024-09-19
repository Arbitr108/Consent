package club.premiumit.consentlab.domain.usecase

import club.premiumit.consentlab.domain.repository.CostsRepository

/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
fun interface CalculateUseCase {
    suspend operator fun invoke(types: List<String>): Int
}

class CalculateUseCaseImpl(
    private val repository: CostsRepository
) : CalculateUseCase {
    override suspend fun invoke(types: List<String>): Int =
        repository.getCosts()
            .asSequence()
            .map { dataType ->
                if (types.contains(dataType.name)) {
                    dataType.value
                } else {
                    0
                }
            }
            .reduce { acc, i -> acc + i }
}