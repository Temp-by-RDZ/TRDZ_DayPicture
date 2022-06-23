package com.trdz.day_picture.w_view.scenes

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.trdz.day_picture.R

class SceneFlash: Fragment() {

	//region Elements
	private var durationBoth = 200L
	//endregion

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		return inflater.inflate(R.layout.scene_flash, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		shine()
	}
	private fun shine() {
		requireActivity().findViewById<FrameLayout>(R.id.white_screen).animate().apply {
			alpha(0.8f)
			duration = durationBoth
			withEndAction { blank() }
			start()
		}
	}
	private fun blank() {
		requireActivity().findViewById<FrameLayout>(R.id.white_screen).animate().apply {
			alpha(0.0f)
			duration = durationBoth
			withEndAction { requireActivity().supportFragmentManager.beginTransaction().detach(this@SceneFlash).commit() }
			start()
		}
	}

	companion object {
		fun newInstance() = SceneFlash()
	}


}

