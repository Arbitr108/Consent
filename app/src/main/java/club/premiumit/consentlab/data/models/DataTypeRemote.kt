package club.premiumit.consentlab.data.models

import club.premiumit.consentlab.domain.model.DataType

data class DataTypeRemote(
    val name: String,
    val value: Int
)

fun DataTypeRemote.toDomain() = DataType(
    name = this.name,
    value = this.value
)
