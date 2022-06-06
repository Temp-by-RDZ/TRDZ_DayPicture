package com.trdz.day_picture.w_view.fragments_windows

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.FragmentWindowPomBinding

class WindowPOM  : Fragment() {

		private var _binding: FragmentWindowPomBinding? = null
		private val binding: FragmentWindowPomBinding
			get() = _binding!!

		override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?
		): View {
			_binding = FragmentWindowPomBinding.inflate(inflater, container, false)
			return binding.root
		}


		override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
			super.onCreateOptionsMenu(menu, inflater)
			inflater.inflate(R.menu.menu_bottom_bar, menu)
		}


		override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
			super.onViewCreated(view, savedInstanceState)
		}


		companion object {
			@JvmStatic
			fun newInstance() = WindowPOM()
		}

		override fun onDestroy() {
			_binding = null
			super.onDestroy()
		}
	}
