package com.trdz.day_picture.w_view

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.trdz.day_picture.z_utility.CONST_MIN_ALPHA
import com.trdz.day_picture.z_utility.CONST_MIN_SCALE

class FragmentNavigationTransformer: ViewPager.PageTransformer {

	override fun transformPage(view: View, position: Float) {
		view.apply {
			when {
				position < -1 -> {
					// This page is way off-screen to the left.
					alpha = 0f
				}
				position <= 1 -> {
					// Modify the default slide transition to shrink the page as well
					val scaleFactor = Math.max(CONST_MIN_SCALE, 1 - Math.abs(position))
					val vertMargin = height * (1 - scaleFactor) / 2
					val horzMargin = width * (1 - scaleFactor) / 2
					translationX = if (position < 0) horzMargin - vertMargin / 2
					else horzMargin + vertMargin / 2

					// Scale the page down (between MIN_SCALE and 1)
					scaleX = scaleFactor
					scaleY = scaleFactor

					// Fade the page relative to its size.
					alpha = (CONST_MIN_ALPHA + (((scaleFactor - CONST_MIN_SCALE) / (1 - CONST_MIN_SCALE)) * (1 - CONST_MIN_ALPHA)))
				}
				else -> {
					// This page is way off-screen to the right.
					alpha = 0f
				}
			}
		}
	}
}
