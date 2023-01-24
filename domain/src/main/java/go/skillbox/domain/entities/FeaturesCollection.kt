package go.skillbox.domain.entities

import go.skillbox.domain.entities.parts.feature.collection.Feature

interface FeaturesCollection {
    val type: String?
    val features: List<Feature>?
}