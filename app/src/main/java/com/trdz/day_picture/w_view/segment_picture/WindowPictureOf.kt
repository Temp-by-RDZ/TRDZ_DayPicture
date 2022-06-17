package com.trdz.day_picture.w_view.segment_picture

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.trdz.day_picture.R
import com.trdz.day_picture.w_view.Leader
import com.trdz.day_picture.w_view.MainActivity
import com.trdz.day_picture.x_view_model.MainViewModel
import com.trdz.day_picture.x_view_model.StatusProcess
import android.view.animation.AlphaAnimation
import androidx.constraintlayout.widget.ConstraintLayout
import coil.clear
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.trdz.day_picture.databinding.FragmentWindowPofBinding
import com.trdz.day_picture.z_utility.KEY_PREFIX
import com.trdz.day_picture.z_utility.PREFIX_EPC
import com.trdz.day_picture.z_utility.PREFIX_MRP
import com.trdz.day_picture.z_utility.PREFIX_POD
import kotlin.concurrent.thread

class WindowPictureOf: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowPofBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!
	private var _bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null
	private val bottomSheetBehavior get() = _bottomSheetBehavior!!
	private lateinit var prefix: String
	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
		_viewModel = null
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			prefix = it.getString(KEY_PREFIX, PREFIX_POD)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowPofBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		_viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val observer = Observer<StatusProcess> { renderData(it) }
		when (prefix) {
			PREFIX_POD -> viewModel.getPodData().observe(viewLifecycleOwner, observer)
			PREFIX_MRP -> viewModel.getPomData().observe(viewLifecycleOwner, observer)
			PREFIX_EPC -> viewModel.getPoeData().observe(viewLifecycleOwner, observer)
		}
		buttonBinds()
		initialize()
	}

	//endregion

	//region Main functional
	private fun buttonBinds() {
		with(binding) {
			_bottomSheetBehavior = BottomSheetBehavior.from(popupSheet.bottomSheetContainer)
			imageView.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED }
			bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
			bottomSheetBehavior.isHideable = true
			bottomSheetBehavior.addBottomSheetCallback(object:
				BottomSheetBehavior.BottomSheetCallback() {
				override fun onStateChanged(bottomSheet: View, newState: Int) {
					when (newState) {
						BottomSheetBehavior.STATE_DRAGGING -> {
						}
						BottomSheetBehavior.STATE_COLLAPSED -> {
							bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
						}
						BottomSheetBehavior.STATE_EXPANDED -> {
						}
						BottomSheetBehavior.STATE_HALF_EXPANDED -> {
						}
						BottomSheetBehavior.STATE_HIDDEN -> {
						}
						BottomSheetBehavior.STATE_SETTLING -> {
						}
					}
				}

				override fun onSlide(bottomSheet: View, slideOffset: Float) {}

			})
		}
	}

	private fun initialize() {
		viewModel.initialize(prefix)
		removeLoad()
	}

	private fun renderData(material: StatusProcess) {
		when (material) {
			StatusProcess.Load -> {
				binding.postLoad.visibility = View.VISIBLE
				bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
				removeLoad()
			}
			is StatusProcess.Error -> {
				binding.postLoad.visibility = View.GONE
				Log.d("@@@", "App - $prefix catch $material.code")
				binding.imageView.clear()
				binding.imageView.setBackgroundResource(R.drawable.nofile)
				binding.popupSheet.title.text = getString(R.string.ERROR_TITLE)
				binding.popupSheet.explanation.text = StringBuilder(getString(R.string.ERROR_DISCRIPTIOn)).apply {
					append("\n")
					append(getString(R.string.Error_code_message))
					append(" ")
					append(material.code)
					append("\n")
					when (material.code) {
						-2 -> append(getString(R.string.error_desc_m2))
						-1 -> append(getString(R.string.error_desc_m1))
						in 200..299 -> append(getString(R.string.error_desc_200))
						in 300..399 -> append(getString(R.string.error_desc_300))
						in 400..499 -> append(getString(R.string.error_desc_400))
						in 500..599 -> append(getString(R.string.error_desc_500))
						else -> append(getString(R.string.error_desc_0))
					}
				}
				bottomSheetBehavior.halfExpandedRatio = 0.35f
				bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
			}
			is StatusProcess.Success -> {
				Log.d("@@@", "App - $prefix render")
				binding.imageView.setBackgroundResource(R.color.black)
				binding.imageView.load(material.data.url) { placeholder(R.drawable.image_still_loading2); error(R.drawable.nofile) }
				binding.postLoad.visibility = View.GONE
				binding.popupSheet.title.text = material.data.name
				binding.popupSheet.explanation.text = material.data.description
			}
			is StatusProcess.Video -> {
				Log.d("@@@", "App - $prefix show")
				binding.youtubePlayer.visibility = View.VISIBLE
			}
		}
	}

	private fun removeLoad() {
		val dispose = AlphaAnimation(1.0f, 0.0f).apply {
			duration = 2000
			startOffset = 500
			fillAfter = true
		}
		binding.loadingLayout.startAnimation(dispose)
		thread {
			while (!dispose.hasEnded()) Thread.sleep(50L)
			Handler(Looper.getMainLooper()).post {
				binding.loadingLayout.clearAnimation()
				binding.loadingLayout.visibility = View.GONE
			}
		}
	}
	//endregion

	companion object {
		@JvmStatic
		fun newInstance(prefix: String) =
			WindowPictureOf().apply {
				arguments = Bundle().apply {
					putString(KEY_PREFIX, prefix)
				}
			}
	}

}