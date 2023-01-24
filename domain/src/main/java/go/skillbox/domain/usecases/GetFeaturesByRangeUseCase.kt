package go.skillbox.domain.usecases

import go.skillbox.domain.exceptions.GetFeaturesByRangeException
import go.skillbox.domain.models.params.GetDataFeaturesByRangeParam
import go.skillbox.domain.models.params.GetFeaturesByRangeParam
import go.skillbox.domain.models.results.FeaturesByRange
import go.skillbox.domain.repositories.FeaturesRepository
import javax.inject.Inject

open class GetFeaturesByRangeUseCase @Inject constructor() {

    @Inject
    protected lateinit var repository: FeaturesRepository

    suspend fun execute(param: GetFeaturesByRangeParam): FeaturesByRange {
        try {
            return FeaturesByRange(
                repository.getFeaturesByRange(
                    GetDataFeaturesByRangeParam(
                        param.longitudeMin,
                        param.longitudeMax,
                        param.latitudeMin,
                        param.latitudeMax
                    )
                )
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw GetFeaturesByRangeException(ex.message ?: "Unknown error")
        }
    }
}