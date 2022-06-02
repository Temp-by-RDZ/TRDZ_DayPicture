package com.trdz.day_picture.w_view.fragments_windows

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.FragmentSettingsBinding
import com.trdz.day_picture.databinding.FragmentWindowNavigationBinding
import com.trdz.day_picture.w_view.MainActivity
import com.trdz.day_picture.z_utility.OPTIONS_KEY
import com.trdz.day_picture.z_utility.THEME_KEY
import com.trdz.day_picture.z_utility.TSET_KEY

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    var themeID = 0

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d("@@@", ""+themeID)
        getCurrentTheme()
        val localInflater = inflater.cloneInContext(ContextThemeWrapper(activity, getRealStylesID(themeID)))
        _binding = FragmentSettingsBinding.inflate(localInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preSelect()
        buttonBinds()
    }

    private fun getCurrentTheme() {
        themeID = requireActivity().getSharedPreferences(OPTIONS_KEY,Context.MODE_PRIVATE).getInt(TSET_KEY,0)
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
                if (tab==null) return
                themeID = tab.position
                requireActivity().getSharedPreferences(OPTIONS_KEY, Context.MODE_PRIVATE).edit().putInt(TSET_KEY, themeID).apply()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_fragment_base, SettingsFragment())
                    .commit()
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
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.cancel.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}