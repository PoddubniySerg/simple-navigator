package go.skillbox.domain.repositories

import go.skillbox.domain.entities.FeaturesCollection
import go.skillbox.domain.entities.FeatureProperties
import go.skillbox.domain.models.params.GetDataFeaturePropertiesByIdParam
import go.skillbox.domain.models.params.GetDataFeaturesByRangeParam

interface FeaturesRepository {

    suspend fun getFeaturesByRange(param: GetDataFeaturesByRangeParam): FeaturesCollection

    suspend fun getFeaturePropertiesById(param: GetDataFeaturePropertiesByIdParam): FeatureProperties
}