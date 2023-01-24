package go.skillbox.simplenavigator.maps.google

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

class LocationCallbackImpl(
    private val map: GoogleMap,
    private val locationSource: LocationSourceObject,
    private var needAnimateCamera: Boolean,
    private var needMoveCamera: Boolean,
    private val setSpeed: (Float) -> Unit
) : LocationCallback() {

    override fun onLocationResult(result: LocationResult) {
        result.lastLocation?.let { location ->
            locationSource.getLocationListener()?.onLocationChanged(location)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                LatLng(location.latitude, location.longitude),
                18f
            )
            setSpeed(location.speed)
            map.setOnMyLocationButtonClickListener {
                needMoveCamera = !needMoveCamera
                return@setOnMyLocationButtonClickListener needMoveCamera
            }
            if (needMoveCamera) {
                if (needAnimateCamera) {
                    map.animateCamera(cameraUpdate)
                } else {
                    needAnimateCamera = true
                    map.moveCamera(cameraUpdate)
                }
            }
        }
    }

    fun needAnimateCamera(isNeed: Boolean) {
        this.needAnimateCamera = isNeed
    }

    fun getNeedAnimateCamera(): Boolean {
        return needAnimateCamera
    }

    fun getNeedMoveCamera(): Boolean {
        return needMoveCamera
    }
}