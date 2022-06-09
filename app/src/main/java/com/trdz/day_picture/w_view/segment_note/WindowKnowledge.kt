package com.trdz.day_picture.w_view.segment_note

import android.os.Bundle
import android.view.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.trdz.day_picture.databinding.FragmentWindowKnowlageBinding

class WindowKnowledge: Fragment() {

	//region Elements
	private var _binding: FragmentWindowKnowlageBinding? = null
	private val binding get() = _binding!!
	//endregion

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowKnowlageBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//val params = (binding.button.layoutParams as CoordinatorLayout.LayoutParams)
		//params.behavior = ButtonBehavior(requireContext())
	//	binding.button.layoutParams = params
	}


	companion object {
		fun newInstance() = WindowKnowledge()
	}


}

