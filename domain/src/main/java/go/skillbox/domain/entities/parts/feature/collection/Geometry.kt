package go.skillbox.domain.entities.parts.feature.collection

interface Geometry {
    val type: String?
    val coordinates: List<Double>?
}