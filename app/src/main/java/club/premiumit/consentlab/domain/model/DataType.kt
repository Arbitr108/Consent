package club.premiumit.consentlab.domain.model

// We need different models for the domain and the data layers at least
// to decouple the layers' dependencies and to avoid to show the data info
// that is not required for the business logic, but only for the data layer
// Some times we may even have separate ui level models for more sophisticated mapping
data class DataType(
    val name: String,
    val value: Int
)
