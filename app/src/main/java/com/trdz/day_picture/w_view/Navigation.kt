package com.trdz.day_picture.w_view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class Navigation(private var fastContainer: Int = 0) {

	fun returnTo(manager: FragmentManager, toId: Int = 0) {
		if (manager.backStackEntryCount <= toId) return
		val entry: FragmentManager.BackStackEntry = manager.getBackStackEntryAt(toId)
		manager.popBackStackImmediate(entry.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
	}

	fun add(manager: FragmentManager, fragment: Fragment?, remember: Boolean = true, container: Int = fastContainer) {
		if (manager.isDestroyed) return
		manager.beginTransaction().apply {
			add(container, fragment!!)
			if (remember) addToBackStack("")
			commit()
		}
	}

	fun replace(manager: FragmentManager, fragment: Fragment?, remember: Boolean = true, container: Int = fastContainer) {
		if (manager.isDestroyed) return
		manager.beginTransaction().apply {
			replace(container, fragment!!)
			setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
			if (remember) addToBackStack("")
			commit()
		}
	}
}