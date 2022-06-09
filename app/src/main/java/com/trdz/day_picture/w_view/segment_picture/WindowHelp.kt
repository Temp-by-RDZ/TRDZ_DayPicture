package com.trdz.day_picture.w_view.segment_picture

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trdz.day_picture.databinding.FragmentWindowHelpBinding
import com.trdz.day_picture.x_view_model.MainViewModel

class WindowHelp: Fragment() {

	private var _binding: FragmentWindowHelpBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_viewModel = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowHelpBinding.inflate(inflater, container, false)
		_viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		buttonBinds()
	}


	private fun buttonBinds() {
		with(binding) {
			back.setOnClickListener{ closeHelp() }
			ok.setOnClickListener{ closeHelp() }
		}
	}

	private fun closeHelp() {
		viewModel.changePage(1)
		requireActivity().supportFragmentManager.popBackStack()
	}
}