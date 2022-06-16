package com.trdz.day_picture.w_view.segment_book

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.FragmentWindowPoeBinding
import com.trdz.day_picture.z_utility.KEY_FINSTANCE

class WindowKnowledge: Fragment() {

	private var _binding: FragmentWindowPoeBinding? = null
	private val binding: FragmentWindowPoeBinding
		get() = _binding!!

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentWindowPoeBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val list = arrayListOf(
			Data("Earth","Earth des",TYPE_EARTH),
			Data("Earth","Earth des",TYPE_EARTH),
			Data("Mars", "Mars des",TYPE_MARS),
			Data("Earth","Earth des",TYPE_EARTH),
			Data("Earth","Earth des",TYPE_EARTH),
			Data("Earth","Earth des",TYPE_EARTH),
			Data("Mars", "Mars des",TYPE_MARS)
		)

		binding.recyclerView.adapter = WindowKnowledgeRecycle(list)
	}


	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.menu_bottom_bar, menu)
	}


	companion object {
		@JvmStatic
		fun newInstance() = WindowKnowledge()
	}

	override fun onDestroy() {
		_binding = null
		super.onDestroy()
	}
}
