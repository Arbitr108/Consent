package club.premiumit.consentlab.domain.usecase

import club.premiumit.consentlab.domain.repository.RulesRepository

fun interface CostAdjustUseCase {
    suspend operator fun invoke(declared: List<String>, total: Int): Double
}

class CostAdjustUseCaseImpl(
    private val repository: RulesRepository
) : CostAdjustUseCase {
    override suspend fun invoke(declared: List<String>, total: Int): Double {
        val adjustment = repository.getRules()
            .sortedBy { it.priority }
            .filter { it.isApplicable(declared) }
            .takeIf { it.isNotEmpty() }
            ?.map { it.apply(total) }
            ?.reduce { acc, d -> acc + d }

        return if(adjustment != null){
            total.toDouble() + adjustment
        } else {
             total.toDouble()
        }
    }
}