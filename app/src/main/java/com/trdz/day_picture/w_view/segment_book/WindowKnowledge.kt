package com.trdz.day_picture.w_view.segment_book

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.day_picture.databinding.FragmentWindowKnowlageDetailsBinding

class WindowKnowledge: Fragment() {

	//region Elements
	private var _binding: FragmentWindowKnowlageDetailsBinding? = null
	private val binding get() = _binding!!
	//endregion

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowKnowlageDetailsBinding.inflate(inflater, container, false)
		return binding.root
	}


	companion object {
		fun newInstance() = WindowKnowledge()
	}


}

