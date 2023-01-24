package go.skillbox.data.repositories

import go.skillbox.domain.entities.FeatureProperties
import go.skillbox.domain.entities.FeaturesCollection
import go.skillbox.domain.models.params.GetDataFeaturePropertiesByIdParam
import go.skillbox.domain.models.params.GetDataFeaturesByRangeParam
import go.skillbox.domain.repositories.FeaturesRepository

open class FeaturesRepositoryImpl(
    private val networkFeaturesRepository: NetworkFeaturesRepository
) : FeaturesRepository {

    override suspend fun getFeaturesByRange(param: GetDataFeaturesByRangeParam): FeaturesCollection {
        return networkFeaturesRepository.getFeaturesByRange(
            param.longitudeMin,
            param.longitudeMax,
            param.latitudeMin,
            param.latitudeMax
        ) ?: throw RuntimeException("Features not found")
    }

    override suspend fun getFeaturePropertiesById(param: GetDataFeaturePropertiesByIdParam): FeatureProperties {
        return networkFeaturesRepository.getFeaturePropertiesById(param.id)
            ?: throw RuntimeException("Features not found")
    }
}