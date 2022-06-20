package com.trdz.day_picture.w_view.segment_book

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.day_picture.R
import com.trdz.day_picture.w_view.Leader
import com.trdz.day_picture.w_view.MainActivity
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.trdz.day_picture.databinding.FragmentWindowPictureBinding
import com.trdz.day_picture.w_view.segment_picture.FragmentNavigation
import kotlin.concurrent.thread
import kotlin.math.ceil

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

	private fun initialize() {


		binding.task.typeface = Typeface.createFromAsset(requireActivity().assets,"azeret.ttf")

		val textSpannable =  binding.placeholder.text

		val spannedString: SpannedString
		val spannableString:SpannableString = SpannableString(textSpannable)
		var spannableStringBuilder: SpannableStringBuilder = SpannableStringBuilder(textSpannable)

		binding.placeholder.setText(spannableStringBuilder, TextView.BufferType.EDITABLE)
		spannableStringBuilder = (binding.placeholder.text as SpannableStringBuilder).apply {
			setSpan(RelativeSizeSpan(1.3f),0,spannableStringBuilder.length,SpannedString.SPAN_EXCLUSIVE_INCLUSIVE)
			val color =  resources.getIntArray(R.array.colors)
			var start =0
			for (j in color.indices) {
				val end= kotlin.math.min((start+ceil(textSpannable.length.toDouble() / color.size)).toInt(),textSpannable.length)
				setSpan(ForegroundColorSpan(color[j]), start, end, SpannedString.SPAN_EXCLUSIVE_INCLUSIVE)
				start = end
				}
			//spannableStringBuilder.insert(start, "VIV")

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

	//endregion

	companion object {
		fun newInstance() = WindowPicture()
	}

}