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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.trdz.day_picture.databinding.FragmentWindowPodBinding
import com.trdz.day_picture.z_utility.KEY_FINSTANCE
import kotlin.concurrent.thread

class WindowPOD: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowPodBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!
	private var _bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null
	private val bottomSheetBehavior get() = _bottomSheetBehavior!!
	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
		_viewModel = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowPodBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		_viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val observer = Observer<StatusProcess> { renderData(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
		buttonBinds()
		initialize()
	}

	//endregion

	//region Main functional
	private fun buttonBinds() {
		with(binding) {
			_bottomSheetBehavior = BottomSheetBehavior.from(popupSheet.bottomSheetContainer)
			imageView.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED }
		}
	}

	private fun initialize() {
		viewModel.start()
		removeLoad()
	}

	private fun renderData(material: StatusProcess) {
		when (material) {
			StatusProcess.Load -> {
				bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
				removeLoad()
			}
			is StatusProcess.Error -> {
				Log.d("@@@", "App - error")
				executors.getExecutor().showToast(requireContext(), getString(R.string.render_show_error) + material.code)
				binding.imageView.setBackgroundResource(R.drawable.nofile)
			}
			is StatusProcess.Success -> {
				binding.imageView.setBackgroundResource(R.color.black)
				Log.d("@@@", "App - success")
				binding.imageView.load(material.data.url) { placeholder(R.drawable.image_still_loading) }
				binding.popupSheet.title.text = material.data.title
				binding.popupSheet.explanation.text = material.data.explanation
				}
			is StatusProcess.Video -> {
			//TODO()
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
		fun newInstance() = WindowPOD()
	}

}