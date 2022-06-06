package com.trdz.day_picture.w_view

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.trdz.day_picture.R
import com.trdz.day_picture.z_utility.WIN_CODE_POD
import com.trdz.day_picture.z_utility.WIN_CODE_POE
import com.trdz.day_picture.z_utility.WIN_CODE_POM

class FragmentNavigationPager(private val fm: FragmentManager, private val context: Context): FragmentStatePagerAdapter(fm) {


	private val bind = arrayOf(WIN_CODE_POE, WIN_CODE_POD, WIN_CODE_POM)

	private val windows = hashMapOf<String,Fragment>()

	fun add(code:String, fragment: Fragment) {
		windows[code] = fragment
	}

	override fun getCount(): Int {
		return windows.size
	}

	override fun getItem(position: Int): Fragment {
		return windows[bind[position]]!!
	}

	override fun getPageTitle(position: Int): CharSequence {
		return when (position) {
			0 -> context.getString(R.string.POE_title)
			1 -> context.getString(R.string.POD_title)
			2 -> context.getString(R.string.POM_title)
			else -> context.getString(R.string.MISSING)
		}
	}

}