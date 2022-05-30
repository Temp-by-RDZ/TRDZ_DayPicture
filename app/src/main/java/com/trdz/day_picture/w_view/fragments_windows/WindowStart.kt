package com.trdz.day_picture.w_view.fragments_windows

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.FragmentWindowStartBinding
import com.trdz.day_picture.w_view.Leader
import com.trdz.day_picture.w_view.MainActivity
import com.trdz.day_picture.x_view_model.MainViewModel
import com.trdz.day_picture.x_view_model.StatusProcess
import com.trdz.day_picture.z_utility.action
import com.trdz.day_picture.z_utility.showSnackBar
import android.view.animation.AlphaAnimation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import kotlin.concurrent.thread


class WindowStart: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowStartBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!
	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
		_viewModel = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowStartBinding.inflate(inflater, container, false)
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
		val bottomSheetBehavior = BottomSheetBehavior.from(binding.popupSheet.bottomSheetContainer)
		binding.imageView.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED }
		binding.floatButton.setOnClickListener { executors.getNavigation().add(requireActivity().supportFragmentManager, WindowSearch()) }
		binding.chipGroup.setOnCheckedChangeListener { _, position -> chipRealization(position)	}
	}

	private fun chipRealization(position:Int) {
		when(position){
			1->viewModel.analyze()
			2->viewModel.start()
			3->executors.getExecutor().showToast(requireContext(), getString(R.string.egs))
		}
	}

	private fun initialize() {
		viewModel.start()
	}

	private fun renderData(material: StatusProcess) {
		removeLoad()
		when (material) {
			is StatusProcess.Error -> {
				Log.d("@@@", "App - error")
				executors.getExecutor().showToast(requireContext(), getString(R.string.render_show_error) + material.code)
			}
			is StatusProcess.Success -> {
				binding.imageView.setBackgroundResource(R.color.black)
				Log.d("@@@", "App - success")
				if (material.data.mediaType == "video") {
					binding.imageView.showSnackBar(getString(R.string.error_this_is_video), Snackbar.LENGTH_INDEFINITE) { action(R.string.error_this_is_video_end) {} }
				}
				else {
					binding.imageView.load(material.data.url)
					binding.popupSheet.title.text = material.data.title
					binding.popupSheet.explanation.text = material.data.explanation
				}
			}
		}
	}

	private fun removeLoad() {
		val dispose = AlphaAnimation(1.0f, 0.0f).apply {
			duration = 1500
			startOffset = 100
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
		fun newInstance() = WindowStart()
	}

}