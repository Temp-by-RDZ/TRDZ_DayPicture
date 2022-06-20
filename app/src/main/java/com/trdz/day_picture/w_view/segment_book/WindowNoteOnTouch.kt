package com.trdz.day_picture.w_view.segment_book

interface WindowNoteOnTouch {
	fun onItemMove(fromPosition: Int, toPosition: Int)
	fun onItemDismiss(position: Int)
}

interface WindowNoteOnTouchHelp {
	fun onItemSelected()
	fun onItemClear()
}