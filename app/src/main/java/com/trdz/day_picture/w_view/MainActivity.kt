package com.trdz.day_picture.w_view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.trdz.day_picture.R
import com.trdz.day_picture.w_view.fragments_windows.WindowHelp
import com.trdz.day_picture.w_view.fragments_windows.WindowSettings
import com.trdz.day_picture.z_utility.KEY_OPTIONS
import com.trdz.day_picture.z_utility.KEY_THEME

class MainActivity: AppCompatActivity(), Leader {

	//region Elements
	private val navigation = Navigation(R.id.container_fragment_base)
	private val executor = Executor()

	//endregion

	override fun onBackPressed() {
		val fragmentList = supportFragmentManager.fragments

		var handled = false
		for (f in fragmentList) {
			if (f is WindowSettings) {
				handled = (f as WindowSettings).onBackPressed()
				if (handled) {
					break
				}
			}
		}

		if (!handled) super.onBackPressed()
	}

	//region Base realization
	override fun onDestroy() {
		executor.stop()
		super.onDestroy()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		themeSettings()
		setContentView(R.layout.activity_main)
		if (savedInstanceState == null) {
			navigation.add(supportFragmentManager, FragmentNavigation.newInstance(),false,R.id.container_fragment_navigation)
		}
	}

	private fun themeSettings() {
		when (getSharedPreferences(KEY_OPTIONS,Context.MODE_PRIVATE).getInt(KEY_THEME,0)) {
			0 -> setTheme(R.style.MyBaseTheme)
			1 -> setTheme(R.style.MyGoldTheme)
			2 -> setTheme(R.style.MyFiolTheme)
		}
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_basic, menu)
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.menu_help) openHelp()
		return super.onOptionsItemSelected(item)
	}

	private fun openHelp() {
		navigation.add(supportFragmentManager, WindowHelp())
	}

	//endregion

	override fun getNavigation() = navigation
	override fun getExecutor() = executor

}