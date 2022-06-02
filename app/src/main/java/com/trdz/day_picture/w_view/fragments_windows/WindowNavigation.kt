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
import com.trdz.day_picture.databinding.FragmentWindowNavigationBinding
import com.trdz.day_picture.x_view_model.StatusMessage
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread


class WindowNavigation: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowNavigationBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!
	private var mood = 0
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
		_binding = FragmentWindowNavigationBinding.inflate(inflater, container, false)
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
			R.id.app_bar_settings -> {
				Log.d("@@@", "App - Settings")
				mood = 1
				executors.getNavigation().replace(requireActivity().supportFragmentManager,SettingsFragment())
			}
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val observer = Observer<StatusMessage> { renderData(it) }
		viewModel.getMessage().observe(viewLifecycleOwner, observer)
		(requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
		setHasOptionsMenu(true)
		buttonBinds()
	}

	//endregion

	//region Main functional
	private fun buttonBinds() {
		with(binding) {
			floatButton.setOnLongClickListener { changeMode() }
			floatButton.setOnClickListener { executors.getNavigation().add(requireActivity().supportFragmentManager, WindowSearch(), true, R.id.container_fragment_navigation) }
			chipGroup.setOnCheckedChangeListener { _, position -> chipRealization(position) }
		}
	}

	private fun changeMode(): Boolean {
		if (isMain) {
			binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
			binding.floatButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_search_24))
		}
		else {
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

	private fun renderData(material: StatusMessage) {
		when (material) {
			is StatusMessage.VideoError -> {
				Log.d("@@@", "Nav - error")
				binding.floatButton.showSnackBar(getString(R.string.error_this_is_video), Snackbar.LENGTH_INDEFINITE) { action(R.string.error_this_is_video_end) {} }
			}
		}
	}

	//endregion

	companion object {
		fun newInstance() = WindowNavigation()
	}

}