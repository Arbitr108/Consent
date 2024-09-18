package club.premiumit.consentlab.domain.strategies

sealed interface CostChangeRule{

    abstract val rules: List<String>
    abstract val value: Int

    fun apply(value: Int) {

    }
    data class Increase(
        override val rules: List<String>,
        override val value: Int) : CostChangeRule
    data class Decrease
        (
        override val rules: List<String>,
        override val value: Int) : CostChangeRule
}

class Calculator {

}