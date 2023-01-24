package go.skillbox.simplenavigator.ui.maps.google

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import go.skillbox.simplenavigator.R
import go.skillbox.simplenavigator.databinding.FragmentMapGoogleBinding
import go.skillbox.simplenavigator.maps.google.LocationCallbackImpl
import go.skillbox.simplenavigator.maps.google.LocationSourceObject
import go.skillbox.simplenavigator.maps.google.MapReadyCallback
import go.skillbox.simplenavigator.models.entities.FeaturesCollectionUi
import go.skillbox.simplenavigator.permissions.MapLocationPermissions
import go.skillbox.simplenavigator.static.MapStateKeys
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GoogleMapFragment : Fragment() {

    companion object {
        private const val SAVE_LONGITUDE_CAMERA_POSITION = "longitude"
        private const val SAVE_LATITUDE_CAMERA_POSITION = "latitude"
        private const val SAVE_ZOOM_CAMERA_POSITION = "zoom"
        private const val SAVE_IS_NEED_ANIMATE_CAMERA = "need_animate_camera"
        private const val SAVE_IS_NEED_MOVE_CAMERA = "need_move_camera"

        fun newInstance(args: Bundle?): GoogleMapFragment {
            val fragment = GoogleMapFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val locationPermissions = MapLocationPermissions(fragment = this) { startLocation() }
    private val locationSource = LocationSourceObject()
    private val mapReadyCallback = MapReadyCallback(locationSource)
    private var locationCallback: LocationCallbackImpl? = null
    private lateinit var fusedClient: FusedLocationProviderClient

    private var binding: FragmentMapGoogleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState == null && arguments == null) {
            arguments = Bundle().apply {
                putBoolean(SAVE_IS_NEED_MOVE_CAMERA, true)
            }
        }
        savedInstanceState?.let { args ->
            arguments = args
        }
        fusedClient = LocationServices.getFusedLocationProviderClient(requireContext())
        binding = FragmentMapGoogleBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_google_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(mapReadyCallback)

        mapReadyCallback.coordinatesFlow.onEach { coordinates ->
            requireArguments().putParcelable(MapStateKeys.VISIBLE_REGION_KEY.key, coordinates)
            setFragmentResult(MapStateKeys.VISIBLE_REGION_KEY.key, requireArguments())
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        mapReadyCallback.mapFlow.onEach {
            setStartPosition()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        mapReadyCallback.snippetFlow.onEach { xid ->
            getFeatureByXid(xid)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        setFragmentResultListener(MapStateKeys.FEATURES_LIST_KEY.key) { requestKey, bundle ->
            addMarkers(requestKey, bundle)
        }
    }

    override fun onStart() {
        super.onStart()
        locationPermissions.check()
    }

    override fun onStop() {
        super.onStop()
        locationCallback?.let { callback ->
            fusedClient.removeLocationUpdates(callback)
            callback.needAnimateCamera(isNeed = false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapReadyCallback.getMap()?.cameraPosition?.let { cameraPosition ->
            outState.putDouble(SAVE_LONGITUDE_CAMERA_POSITION, cameraPosition.target.longitude)
            outState.putDouble(SAVE_LATITUDE_CAMERA_POSITION, cameraPosition.target.latitude)
            outState.putFloat(SAVE_ZOOM_CAMERA_POSITION, cameraPosition.zoom)
        }
        locationCallback?.let {
            outState.putBoolean(SAVE_IS_NEED_ANIMATE_CAMERA, it.getNeedAnimateCamera())
            outState.putBoolean(SAVE_IS_NEED_MOVE_CAMERA, it.getNeedMoveCamera())
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocation() {
        val map = mapReadyCallback.getMap() ?: return
        if (locationCallback == null) {
            locationCallback =
                LocationCallbackImpl(
                    map,
                    locationSource,
                    needAnimateCamera = arguments?.getBoolean(SAVE_IS_NEED_ANIMATE_CAMERA) ?: false,
                    needMoveCamera = arguments?.getBoolean(SAVE_IS_NEED_MOVE_CAMERA) ?: true
                )
                { speed ->
                    requireArguments().putFloat(MapStateKeys.SPEED_LISTENER_KEY.key, speed)
                    setFragmentResult(MapStateKeys.SPEED_LISTENER_KEY.key, requireArguments())
                }
        }
        map.isMyLocationEnabled = true
        val request = LocationRequest.Builder(1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
        fusedClient.requestLocationUpdates(
            request,
            locationCallback!!,
            Looper.getMainLooper()
        )
    }

    private fun getFeatureByXid(xid: String) {
        mapReadyCallback.getMap()?.cameraPosition?.let { cameraPosition ->
            requireArguments().putDouble(
                SAVE_LONGITUDE_CAMERA_POSITION,
                cameraPosition.target.longitude
            )
            requireArguments().putDouble(
                SAVE_LATITUDE_CAMERA_POSITION,
                cameraPosition.target.latitude
            )
            requireArguments().putFloat(SAVE_ZOOM_CAMERA_POSITION, cameraPosition.zoom)
        }
        locationCallback?.let {
            requireArguments().putBoolean(
                SAVE_IS_NEED_ANIMATE_CAMERA,
                it.getNeedAnimateCamera()
            )
            requireArguments().putBoolean(SAVE_IS_NEED_MOVE_CAMERA, it.getNeedMoveCamera())
        }
        requireArguments().putString(MapStateKeys.FEATURE_XID_KEY.key, xid)
        setFragmentResult(MapStateKeys.SAVE_OUT_STATE_KEY.key, requireArguments())
    }

    private fun setStartPosition() {
        locationPermissions.check()
        mapReadyCallback.setStartPosition(
            requireArguments().getDouble(SAVE_LONGITUDE_CAMERA_POSITION),
            requireArguments().getDouble(SAVE_LATITUDE_CAMERA_POSITION),
            requireArguments().getFloat(SAVE_ZOOM_CAMERA_POSITION)
        )
    }

    private fun addMarkers(key: String, bundle: Bundle) {
        val features =
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
                bundle.getParcelable<FeaturesCollectionUi>(key)?.features ?: return
            else
                bundle.getParcelable(key, FeaturesCollectionUi::class.java)?.features ?: return

        val map = mapReadyCallback.getMap() ?: return
        for (feature in features) {
            feature.properties?.name?.let { name ->
                if (name.isEmpty()) return@let
                feature.geometry?.coordinates?.let { coordinatesArray ->
                    val pos = LatLng(coordinatesArray[1], coordinatesArray[0])
                    map.addMarker(
                        MarkerOptions()
                            .position(pos)
                            .title(name)
                            .snippet(feature.properties.xid)
                    )
                }
            }
        }
    }
}