package go.skillbox.domain.models.params

data class GetFeaturesByRangeParam(
    val longitudeMin: Double,
    val longitudeMax: Double,
    val latitudeMin: Double,
    val latitudeMax: Double
)
