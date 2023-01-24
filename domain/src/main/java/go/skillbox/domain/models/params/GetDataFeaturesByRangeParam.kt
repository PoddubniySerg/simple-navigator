package go.skillbox.domain.models.params

data class GetDataFeaturesByRangeParam(
    val longitudeMin: Double,
    val longitudeMax: Double,
    val latitudeMin: Double,
    val latitudeMax: Double
)
