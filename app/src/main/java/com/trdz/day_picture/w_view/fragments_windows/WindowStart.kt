package com.trdz.day_picture.w_view.fragments_windows

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
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
import androidx.core.content.ContextCompat
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlin.concurrent.thread


class WindowStart: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowStartBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!
	var isMain = true
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
	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.menu_bottom_bar, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.app_bar_fav -> Log.d("@@@", "App - Favorite")
			R.id.app_bar_settings -> Log.d("@@@", "App - Settings")
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val observer = Observer<StatusProcess> { renderData(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
		(requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
		setHasOptionsMenu(true)
		buttonBinds()
		initialize()
	}

	//endregion

	//region Main functional
	private fun buttonBinds() {
		with(binding) {
			val bottomSheetBehavior = BottomSheetBehavior.from(popupSheet.bottomSheetContainer)
			floatButton.setOnLongClickListener { changeMode() }
			imageView.setOnClickListener { bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED }
			floatButton.setOnClickListener { executors.getNavigation().add(requireActivity().supportFragmentManager, WindowSearch()) }
			chipGroup.setOnCheckedChangeListener { _, position -> chipRealization(position) }
		}
	}

	private fun changeMode(): Boolean {
		if (isMain) {
			binding.bottomAppBar.navigationIcon = null
			binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
			binding.floatButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_search_24))
		}
		else {
			binding.bottomAppBar.navigationIcon = (ContextCompat.getDrawable(requireContext(), R.drawable.ic_favourite_menu))
			binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
			binding.floatButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_wikipedia))
		}
		isMain = !isMain
		return true
	}

	private fun chipRealization(position: Int) {
		when (position) {
			1 -> viewModel.analyze()
			2 -> viewModel.start()
			3 -> executors.getExecutor().showToast(requireContext(), getString(R.string.egs))
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