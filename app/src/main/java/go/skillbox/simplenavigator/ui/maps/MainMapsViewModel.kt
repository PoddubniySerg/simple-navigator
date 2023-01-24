package go.skillbox.simplenavigator.ui.maps

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import go.skillbox.domain.models.params.GetFeaturePropertiesByIdParam
import go.skillbox.domain.models.params.GetFeaturesByRangeParam
import go.skillbox.domain.usecases.GetFeaturePropertiesByIdUseCase
import go.skillbox.domain.usecases.GetFeaturesByRangeUseCase
import go.skillbox.simplenavigator.models.entities.FeaturePropertiesUi
import go.skillbox.simplenavigator.models.entities.FeaturesCollectionUi
import go.skillbox.simplenavigator.models.parcelable.VisibleRegionCoordinates
import go.skillbox.simplenavigator.states.LoadDataState
import go.skillbox.simplenavigator.utils.Converter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainMapsViewModel @Inject constructor() : ViewModel() {

    @Inject
    protected lateinit var getFeaturesByRange: GetFeaturesByRangeUseCase

    @Inject
    protected lateinit var getFeatureById: GetFeaturePropertiesByIdUseCase

    @Inject
    protected lateinit var converter: Converter

    private var mapSavedState: Bundle? = null

    private val _loadState = MutableStateFlow(LoadDataState.COMPLETED)
    val loadState = _loadState.asStateFlow()

    private val _errorFlow = Channel<Exception>()
    val errorFlow = _errorFlow.receiveAsFlow()

    private val _speedFlow = Channel<Float>()
    val speedFlow = _speedFlow.receiveAsFlow()

    private val _featuresFlow = Channel<FeaturesCollectionUi>()
    val featuresFlow = _featuresFlow.receiveAsFlow()

    private val _featureFlow = Channel<FeaturePropertiesUi>()
    val featureFlow = _featureFlow.receiveAsFlow()

    fun setSpeed(speed: Float) {
        viewModelScope.launch { _speedFlow.send(speed) }
    }

    fun getFeaturesByCoordinates(visibleRegion: VisibleRegionCoordinates?) {
        visibleRegion ?: return
        viewModelScope.launch {
            try {
                _loadState.value = LoadDataState.LOADING
                val featuresCollection = getFeaturesByRange.execute(
                    GetFeaturesByRangeParam(
                        visibleRegion.longitudeMin,
                        visibleRegion.longitudeMax,
                        visibleRegion.latitudeMin,
                        visibleRegion.latitudeMax
                    )
                ).result
                _featuresFlow.send(converter.convert(featuresCollection))
                _loadState.value = LoadDataState.COMPLETED
            } catch (ex: Exception) {
                _errorFlow.send(ex)
                _loadState.value = LoadDataState.ERROR
            }
        }
    }

    fun getFeature(xid: String) {
        viewModelScope.launch {
            try {
                _loadState.value = LoadDataState.LOADING
                val feature = getFeatureById.execute(GetFeaturePropertiesByIdParam(xid)).result
                _featureFlow.send(converter.convert(feature))
                _loadState.value = LoadDataState.COMPLETED
            } catch (ex: Exception) {
                _errorFlow.send(ex)
                _loadState.value = LoadDataState.ERROR
            }
        }
    }

    fun getMapSavedState(): Bundle? {
        return mapSavedState
    }

    fun saveMapState(savedState: Bundle) {
        mapSavedState = savedState
    }
}