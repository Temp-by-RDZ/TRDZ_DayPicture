package com.trdz.day_picture.w_view.segment_book

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.FragmentWindowPoeBinding

class WindowPOE  : Fragment() {

		private var _binding: FragmentWindowPoeBinding? = null
		private val binding: FragmentWindowPoeBinding
			get() = _binding!!

		override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?
		): View {
			_binding = FragmentWindowPoeBinding.inflate(inflater, container, false)
			return binding.root
		}


		override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
			super.onCreateOptionsMenu(menu, inflater)
			inflater.inflate(R.menu.menu_bottom_bar, menu)
		}


	companion object {
			@JvmStatic
			fun newInstance() = WindowPOE()
		}

		override fun onDestroy() {
			_binding = null
			super.onDestroy()
		}
	}
