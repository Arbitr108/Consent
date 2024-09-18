package club.premiumit.consentlab.core

infix fun Number.percentOf(value: Number): Double {
    return if (this.toDouble() == 0.0) 0.0
    else (value.toDouble() * ((this.toDouble()) / 100.0))
}
