package go.skillbox.simplenavigator.ui.maps

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import go.skillbox.simplenavigator.App
import go.skillbox.simplenavigator.R
import go.skillbox.simplenavigator.databinding.FragmentMainMapsBinding
import go.skillbox.simplenavigator.models.parcelable.VisibleRegionCoordinates
import go.skillbox.simplenavigator.permissions.NotificationsPermission
import go.skillbox.simplenavigator.states.LoadDataState
import go.skillbox.simplenavigator.static.MapStateKeys
import go.skillbox.simplenavigator.ui.MainActivity
import go.skillbox.simplenavigator.ui.maps.google.GoogleMapFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.random.Random

@AndroidEntryPoint
class MainMapsFragment : Fragment() {

    companion object {
        private const val FEATURE_PROPERTIES_KEY = "feature_properties"
    }

    private val viewModel by activityViewModels<MainMapsViewModel>()
    private var binding: FragmentMainMapsBinding? = null

    private val notificationsPermission = NotificationsPermission(
        this,
        { createNotification() },
        { warning -> sendWarning(warning) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments = arguments ?: Bundle()
        if (savedInstanceState == null) {
            val savedMapState = viewModel.getMapSavedState()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.map_fragment, GoogleMapFragment.newInstance(savedMapState))
                .commit()
        }
        binding = FragmentMainMapsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.speedTextView?.setOnClickListener {
            checkPermissions()
//            FirebaseCrashlytics.getInstance().log("Crashlytics log")
//            try {
//                throw Exception("Test exception")
//            }catch (ex: Exception) {
//                FirebaseCrashlytics.getInstance().recordException(ex)
//            }
        }

        viewModel.loadState.onEach { state ->
            when (state) {
                LoadDataState.LOADING -> binding?.progressBar?.isVisible = true
                else -> binding!!.progressBar.isVisible = false
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.errorFlow.onEach { error ->
            Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.speedFlow.onEach { speed ->
            binding?.speedTextView?.text = getString(R.string.speed_text_formatted, speed)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.featuresFlow.onEach { features ->
            requireArguments().putParcelable(MapStateKeys.FEATURES_LIST_KEY.key, features)
            setFragmentResult(MapStateKeys.FEATURES_LIST_KEY.key, requireArguments())
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.featureFlow.onEach { feature ->
            val args = Bundle()
            args.putParcelable(FEATURE_PROPERTIES_KEY, feature)
            findNavController().findDestination(R.id.featureDetailsFragment)?.label = feature.name
            findNavController()
                .navigate(
                    R.id.action_mainMapsFragment_to_featureDetailsFragment,
                    args
                )
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        setFragmentResultListener(MapStateKeys.SPEED_LISTENER_KEY.key) { requestKey, bundle ->
            viewModel.setSpeed(bundle.getFloat(requestKey))
        }

        setFragmentResultListener(MapStateKeys.VISIBLE_REGION_KEY.key) { requestKey, bundle ->
            val coordinates =
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU)
                    bundle.getParcelable(requestKey)
                else bundle.getParcelable(requestKey, VisibleRegionCoordinates::class.java)
            viewModel.getFeaturesByCoordinates(coordinates)

        }

        setFragmentResultListener(MapStateKeys.SAVE_OUT_STATE_KEY.key) { _, savedState ->
            viewModel.saveMapState(savedState)
            viewModel.getFeature(
                savedState.getString(MapStateKeys.FEATURE_XID_KEY.key)
                    ?: return@setFragmentResultListener
            )
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.d("token fcm", it.result)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) notificationsPermission.check()
        else createNotification()
    }

    @SuppressLint("UnspecifiedImmutableFlag", "MissingPermission")
    private fun createNotification() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        else
            PendingIntent.getActivity(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        val notificationId = Random.nextInt()
        val notification = NotificationCompat.Builder(requireContext(), App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Test notification")
            .setContentText("Description of the test notification - NOTIFICATION_ID $notificationId")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(requireContext()).notify(notificationId, notification)
    }

    private fun sendWarning(warning: String) {
        Toast.makeText(requireContext(), warning, Toast.LENGTH_SHORT).show()
    }
}