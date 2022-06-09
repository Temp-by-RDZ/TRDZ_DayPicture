package com.trdz.day_picture.w_view.segment_note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.FragmentGlobalNavigationBinding
import com.trdz.day_picture.x_view_model.MainViewModel
import com.trdz.day_picture.w_view.Leader
import com.trdz.day_picture.w_view.MainActivity
import com.trdz.day_picture.z_utility.*
import kotlinx.android.synthetic.main.fragment_navigation.*

class FragmentNavigation: Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentGlobalNavigationBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!
	private var mood = 1
	var isMain = true
	//endregion

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
		_viewModel = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentGlobalNavigationBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		_viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		buttonBinds()
		initialization()
	}
	//endregion

	//region Menu realization
	//endregion


	//region Main functional
	private fun initialization() {
		binding.bottomNavigation.setOnItemSelectedListener { item ->
			when (item.itemId) {
				R.id.action_bottom_navigation_picture -> {

					executors.getNavigation().add(requireActivity().supportFragmentManager,WindowPicture(),false)
					//executors.getNavigation().replace(requireActivity().supportFragmentManager, com.trdz.day_picture.w_view.segment_picture.FragmentNavigation(), false, R.id.container_fragment_navigation)
				}
				R.id.action_bottom_navigation_note -> {
					executors.getNavigation().add(requireActivity().supportFragmentManager,WindowNote(),false)
				}
				R.id.action_bottom_navigation_knowledge -> {
					executors.getNavigation().add(requireActivity().supportFragmentManager,WindowKnolage(),false)
				}
			}
			true
		}
	}
	private fun buttonBinds() {
	}


	//endregion
	companion object {
		fun newInstance() = FragmentNavigation()
	}

}