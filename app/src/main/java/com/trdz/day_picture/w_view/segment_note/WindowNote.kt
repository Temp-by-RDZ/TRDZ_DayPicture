package com.trdz.day_picture.w_view.segment_note

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
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
import com.trdz.day_picture.databinding.FragmentWindowNoteBinding
import com.trdz.day_picture.databinding.FragmentWindowPodBinding
import kotlin.concurrent.thread

class WindowNote: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowNoteBinding? = null
	private val binding get() = _binding!!
	private val duration = 2000L
	private var isOpen = false
	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowNoteBinding.inflate(inflater, container, false)
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
		binding.plusImageview.setOnClickListener {
			if (isOpen) addingClose()
			else addingOpen()
			isOpen = !isOpen
		}
	}

	private fun addingOpen() {

		ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 0f, 360f).setDuration(duration).start()
		ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -130f).setDuration(duration).start()
		ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -260f).setDuration(duration).start()
		binding.optionOneContainer.animate().alpha(1f).setDuration(duration).setListener(object: AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				super.onAnimationEnd(animation)
				binding.optionOneContainer.isClickable = true
			}
		})
		binding.optionTwoContainer.animate().alpha(1f).setDuration(duration).setListener(object: AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				super.onAnimationEnd(animation)
				binding.optionTwoContainer.isClickable = true
			}
		})
	}

	private fun addingClose() {

		ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 360f, 0f).setDuration(duration).start()
		ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f).setDuration(duration).start()
		ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f).setDuration(duration).start()
		binding.optionOneContainer.animate().alpha(0f).setDuration(duration).setListener(object: AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				super.onAnimationEnd(animation)
				binding.optionOneContainer.isClickable = false
			}
		})
		binding.optionTwoContainer.animate().alpha(0f).setDuration(duration).setListener(object: AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				super.onAnimationEnd(animation)
				binding.optionTwoContainer.isClickable = false
			}
		})
	}

	private fun initialize() {
	}

	//endregion

	companion object {
		fun newInstance() = WindowNote()
	}

}