package go.skillbox.simplenavigator.ui.maps

import android.graphics.Insets
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import go.skillbox.simplenavigator.R
import go.skillbox.simplenavigator.databinding.FragmentFeatureDetailsBinding
import go.skillbox.simplenavigator.models.entities.FeaturePropertiesUi


class FeatureDetailsFragment : Fragment() {

    companion object {
        private const val FEATURE_PROPERTIES_KEY = "feature_properties"
    }

    private var binding: FragmentFeatureDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeatureDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            val feature =
                requireArguments().getParcelable<FeaturePropertiesUi>(FEATURE_PROPERTIES_KEY)
            it.featureDescriptionTextView.text =
                feature?.info?.description ?: feature?.wikipediaExtracts?.text
            Glide.with(this)
                .load(feature?.imageUrl)
                .error(
                    Glide.with(this).load(
                        loadPreviewImage(
                            it.featureImageView.layoutParams as ConstraintLayout.LayoutParams,
                            feature
                        )
                    )
                )
                .placeholder(R.drawable.baseline_image_search_24)
                .into(it.featureImageView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun loadPreviewImage(
        constraintParams: ConstraintLayout.LayoutParams,
        feature: FeaturePropertiesUi?
    ): String? {
        val imageWidth = feature?.preview?.width
        val imageHeight = feature?.preview?.height ?: 0
        val screenWidth =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val windowMetrics = requireActivity().windowManager.currentWindowMetrics
                val insets: Insets = windowMetrics.windowInsets
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                (windowMetrics.bounds.width() - insets.left - insets.right).toFloat()
            } else {
                val displayMetrics = DisplayMetrics()
                requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.widthPixels.toFloat()
            }
        val scale: Float = screenWidth.div(imageWidth ?: 1)
        val newHeight = imageHeight * scale

        constraintParams.height = newHeight.toInt()
        return feature?.preview?.sourceUrl
    }
}