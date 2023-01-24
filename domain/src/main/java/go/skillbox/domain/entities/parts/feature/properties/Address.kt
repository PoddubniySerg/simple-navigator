package go.skillbox.domain.entities.parts.feature.properties

interface Address {
    val town: String?
    val state: String?
    val county: String?
    val country: String?
    val postcode: String?
    val countryCode: String?
}