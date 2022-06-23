package com.trdz.day_picture.w_view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.animation.AlphaAnimation
import androidx.fragment.app.Fragment
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.FragmentWindowKnowlageDetailsBinding
import com.trdz.day_picture.databinding.FragmentWindowStartBinding
import com.trdz.day_picture.w_view.segment_picture.FragmentNavigation
import kotlin.concurrent.thread

class WindowStart: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowStartBinding? = null
	private val binding get() = _binding!!
	//endregion

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowStartBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.firstView.animate().apply {
			alpha(0.0f)
			duration = 3900L
			withEndAction { requireActivity().supportFragmentManager.beginTransaction().detach(this@WindowStart).commit()  }
			start()
		}
		Handler(Looper.getMainLooper()).postDelayed({
			executors.getNavigation().add(requireActivity().supportFragmentManager, FragmentNavigation.newInstance(true), false, R.id.container_fragment_navigation)
		}, 100L)
	}

	companion object {
		fun newInstance() = WindowStart()
	}
}

