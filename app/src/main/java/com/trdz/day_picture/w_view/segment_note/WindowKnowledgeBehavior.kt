package com.trdz.day_picture.w_view.segment_note

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class WindowKnowledgeBehavior(
	context: Context,
	attrs: AttributeSet? = null
) : CoordinatorLayout.Behavior<View>(context, attrs) {


	override fun layoutDependsOn(
		parent: CoordinatorLayout,
		child: View,
		dependency: View
	): Boolean {
		return (dependency is AppBarLayout)
	}

	override fun onDependentViewChanged(
		parent: CoordinatorLayout,
		child: View,
		dependency: View
	): Boolean {
		Log.d("","")
		if(dependency is AppBarLayout){
			val bar =  dependency as AppBarLayout
			child.y = 0+bar.height.toFloat()+bar.y
		}else{

		}
		return super.onDependentViewChanged(parent, child, dependency)
	}

}