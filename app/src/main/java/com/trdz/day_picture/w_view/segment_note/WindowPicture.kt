package com.trdz.day_picture.w_view.segment_note

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
import com.trdz.day_picture.databinding.FragmentWindowPictureBinding
import com.trdz.day_picture.databinding.FragmentWindowPodBinding
import com.trdz.day_picture.w_view.segment_picture.FragmentNavigation
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class WindowPicture: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowPictureBinding? = null
	private val binding get() = _binding!!
	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowPictureBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		buttonBinds()
		initialize()
	}

	//endregion

	//region Main functional
	private fun buttonBinds() {
		binding.goToPicture.setOnClickListener {
			goToPicture()
		}
	}

	private fun goToPicture() {
		executors.getNavigation().replace(requireActivity().supportFragmentManager, FragmentNavigation(), false, R.id.container_fragment_navigation)

		val dispose = AlphaAnimation(1.0f, 0.0f).apply {
			duration = 2000
			fillAfter = true
		}
		binding.goToPicture.startAnimation(dispose)
		thread {
			while (!dispose.hasEnded()) Thread.sleep(50L)
			requireActivity().supportFragmentManager.beginTransaction().detach(this).commit()
		}

	}

	private fun initialize() {
	}

	//endregion

	companion object {
		fun newInstance() = WindowPicture()
	}

}