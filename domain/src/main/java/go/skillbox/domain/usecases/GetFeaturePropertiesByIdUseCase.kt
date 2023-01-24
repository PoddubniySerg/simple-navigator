package go.skillbox.domain.usecases

import go.skillbox.domain.exceptions.GetFeaturePropertiesByIdException
import go.skillbox.domain.models.params.GetDataFeaturePropertiesByIdParam
import go.skillbox.domain.models.params.GetFeaturePropertiesByIdParam
import go.skillbox.domain.models.results.FeatureById
import go.skillbox.domain.repositories.FeaturesRepository
import javax.inject.Inject

open class GetFeaturePropertiesByIdUseCase @Inject constructor() {

    @Inject
    protected lateinit var repository: FeaturesRepository

    suspend fun execute(param: GetFeaturePropertiesByIdParam): FeatureById {
        try {
            return FeatureById(
                repository
                    .getFeaturePropertiesById(
                        GetDataFeaturePropertiesByIdParam(param.id)
                    )
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw GetFeaturePropertiesByIdException(ex.message ?: "Unknown error")
        }
    }
}