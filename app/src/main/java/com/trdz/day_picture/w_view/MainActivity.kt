package com.trdz.day_picture.w_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trdz.day_picture.R
import com.trdz.day_picture.w_view.fragments_windows.WindowStart

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
		setContentView(R.layout.activity_main)
		if (savedInstanceState == null) navigation.replace(supportFragmentManager, WindowStart.newInstance(), false)
	}

	//endregion

	override fun getNavigation() = navigation
	override fun getExecutor() = executor

}