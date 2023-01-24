package go.skillbox.domain.entities.parts.feature.properties

interface Bbox {
    val longitudeMin: Double?
    val longitudeMax: Double?
    val latitudeMin: Double?
    val latitudeMax: Double?
}