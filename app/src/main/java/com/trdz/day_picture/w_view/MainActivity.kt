package com.trdz.day_picture.w_view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.trdz.day_picture.R
import com.trdz.day_picture.w_view.fragments_windows.WindowHelp
import com.trdz.day_picture.w_view.fragments_windows.WindowNavigation
import com.trdz.day_picture.w_view.fragments_windows.WindowStart
import com.trdz.day_picture.z_utility.OPTIONS_KEY
import com.trdz.day_picture.z_utility.THEME_KEY

class MainActivity: AppCompatActivity(), Leader {

	//region Elements
	private val navigation = Navigation(R.id.container_fragment_base)
	private val executor = Executor()

	//endregion

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
			navigation.replace(supportFragmentManager, WindowStart.newInstance(), false)
			navigation.add(supportFragmentManager, WindowNavigation.newInstance(),false,R.id.container_fragment_navigation)
		}
	}

	private fun themeSettings() {
		when (getSharedPreferences(OPTIONS_KEY,Context.MODE_PRIVATE).getInt(THEME_KEY,0)) {
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