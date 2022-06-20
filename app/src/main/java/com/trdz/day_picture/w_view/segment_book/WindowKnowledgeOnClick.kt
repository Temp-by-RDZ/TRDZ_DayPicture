package com.trdz.day_picture.w_view.segment_book

interface WindowKnowledgeOnClick {
	fun onItemClick(data: DataTheme, position: Int)
	fun onItemClickLong(data: DataTheme, position: Int)
	fun onItemClickSpecial(data: DataTheme, position: Int)
}
