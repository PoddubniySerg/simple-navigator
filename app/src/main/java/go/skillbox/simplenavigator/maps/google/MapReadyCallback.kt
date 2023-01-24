package go.skillbox.simplenavigator.maps.google

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import go.skillbox.simplenavigator.models.parcelable.VisibleRegionCoordinates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MapReadyCallback(
    private val locationSource: LocationSource
) : OnMapReadyCallback {

    companion object {
        private const val SCREEN_CHANGING_DELAY = 500L
    }

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _snippetFlow = Channel<String>()
    val snippetFlow = _snippetFlow.receiveAsFlow()

    private var _map: GoogleMap? = null
    fun getMap() = _map

    private val _mapFlow = Channel<GoogleMap>()
    val mapFlow = _mapFlow.receiveAsFlow()

    private var _coordinatesFlow = Channel<VisibleRegionCoordinates>()
    val coordinatesFlow = _coordinatesFlow.receiveAsFlow()

    private var isRequestExecuting = false

    override fun onMapReady(googleMap: GoogleMap) {
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        _map = googleMap
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.setLocationSource(locationSource)
        _map!!.setOnCameraMoveListener { getVisibleRegionCoordinates() }
        _map!!.setOnInfoWindowClickListener { marker ->
            scope.launch {
                _snippetFlow.send(marker.snippet ?: return@launch)
            }
        }
        scope.launch { _mapFlow.send(googleMap) }
    }

    private fun getVisibleRegionCoordinates() {
        if (!isRequestExecuting) {
            _map?.let { map ->
                scope.launch {
                    val visibleRegion = map.projection.visibleRegion.latLngBounds
                    val coordinates = VisibleRegionCoordinates(
                        visibleRegion.southwest.longitude,
                        visibleRegion.northeast.longitude,
                        visibleRegion.southwest.latitude,
                        visibleRegion.northeast.latitude
                    )
                    isRequestExecuting = true
                    _coordinatesFlow.send(coordinates)
                    delay(SCREEN_CHANGING_DELAY)
                    isRequestExecuting = false
                }
            }
        }
    }

    fun setStartPosition(longitude: Double, latitude: Double, zoom: Float) {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
            LatLng(latitude, longitude),
            zoom
        )
        _map?.moveCamera(cameraUpdate)
        getVisibleRegionCoordinates()
    }
}