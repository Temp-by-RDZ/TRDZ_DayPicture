package com.trdz.day_picture.w_view.segment_book

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.day_picture.R
import com.trdz.day_picture.w_view.Leader
import com.trdz.day_picture.w_view.MainActivity
import android.view.animation.AlphaAnimation
import com.trdz.day_picture.databinding.FragmentWindowPictureBinding
import com.trdz.day_picture.w_view.segment_picture.FragmentNavigation
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