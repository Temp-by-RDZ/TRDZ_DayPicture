package com.trdz.day_picture.w_view.fragments_windows

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.trdz.day_picture.databinding.FragmentSettingsBinding
import com.trdz.day_picture.databinding.FragmentWindowNavigationBinding
import com.trdz.day_picture.w_view.MainActivity
import com.trdz.day_picture.z_utility.OPTIONS_KEY
import com.trdz.day_picture.z_utility.THEME_KEY


class SettingsFragment : Fragment() {


    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    var themeID = 0

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preSelect()
        buttonBinds()
    }

    private fun preSelect() {
        themeID = requireActivity().getSharedPreferences(OPTIONS_KEY,Context.MODE_PRIVATE).getInt(THEME_KEY,0)
        val tab = binding.tabLayout.getTabAt(themeID)
        tab?.select()
    }

    private fun buttonBinds() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab==null) return
                themeID = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }
        })
        binding.confirm.setOnClickListener {
            val parentActivity = (requireActivity() as MainActivity)
            parentActivity.getSharedPreferences(OPTIONS_KEY, Context.MODE_PRIVATE).edit().putInt(THEME_KEY, themeID).apply()
            parentActivity.recreate()
        }
    }

}