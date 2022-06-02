package com.trdz.day_picture.w_view.fragments_windows

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.trdz.day_picture.R

class WindowHelp: Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_window_help, container, false)
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		menu.findItem(R.id.menu_help).isVisible = false
		super.onCreateOptionsMenu(menu, inflater)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setHasOptionsMenu(true)
	}
}