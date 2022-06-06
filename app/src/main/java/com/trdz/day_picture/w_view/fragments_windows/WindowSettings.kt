package com.trdz.day_picture.w_view.fragments_windows

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.FragmentWindowSettingsBinding
import com.trdz.day_picture.w_view.Leader
import com.trdz.day_picture.w_view.MainActivity
import com.trdz.day_picture.x_view_model.MainViewModel
import com.trdz.day_picture.z_utility.KEY_OPTIONS
import com.trdz.day_picture.z_utility.KEY_THEME
import com.trdz.day_picture.z_utility.KEY_TSET

class WindowSettings : Fragment() {

    private var _executors: Leader? = null
    private val executors get() = _executors!!
    private var _binding: FragmentWindowSettingsBinding? = null
    private val binding get() = _binding!!
    private var _viewModel: MainViewModel? = null
    private val viewModel get() = _viewModel!!
    var themeID = 0

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
    }

    fun onBackPressed(): Boolean {
        viewModel.changePage(1)
        return false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        getCurrentTheme()
        val localInflater = inflater.cloneInContext(ContextThemeWrapper(activity, getRealStylesID(themeID)))
        _binding = FragmentWindowSettingsBinding.inflate(localInflater)
        _viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        _executors = (requireActivity() as MainActivity)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preSelect()
        buttonBinds()
    }

    private fun getCurrentTheme() {
        themeID = requireActivity().getSharedPreferences(KEY_OPTIONS,Context.MODE_PRIVATE).getInt(KEY_TSET,0)
    }

    private fun getRealStylesID(currentTheme: Int): Int {
        return when (currentTheme) {
            0 -> R.style.MyBaseTheme
            1 -> R.style.MyGoldTheme
            3 -> R.style.MyFiolTheme
            else -> 0
        }
    }

    private fun preSelect() {
        val tab = binding.tabLayout.getTabAt(themeID)
        tab?.select()
    }

    private fun buttonBinds() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.changePage(-1)
                if (tab==null) return
                themeID = tab.position
                requireActivity().getSharedPreferences(KEY_OPTIONS, Context.MODE_PRIVATE).edit().putInt(KEY_TSET, themeID).apply()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_fragment_base, WindowSettings())
                    .addToBackStack("")
                    .commit()
                requireActivity().onBackPressed()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        binding.confirm.setOnClickListener {
            val parentActivity = (requireActivity() as MainActivity)
            parentActivity.getSharedPreferences(KEY_OPTIONS, Context.MODE_PRIVATE).edit().putInt(KEY_THEME, themeID).apply()
            parentActivity.recreate()
            requireActivity().onBackPressed()
        }
        binding.cancel.setOnClickListener {
            requireActivity().onBackPressed()}
    }
}