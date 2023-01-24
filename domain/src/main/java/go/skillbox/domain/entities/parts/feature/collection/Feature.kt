package go.skillbox.domain.entities.parts.feature.collection

interface Feature {
    val id: String
    val type: String?
    val geometry: Geometry?
    val properties: Properties?
}