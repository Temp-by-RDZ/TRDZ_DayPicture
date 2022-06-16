package com.trdz.day_picture.w_view.segment_book

interface WindowKnowledgeOnClick {
	fun onItemClick(data: Data,position: Int)
	fun onItemClickLong(data: Data,position: Int)
	fun onItemClickSpecial(data: Data,position: Int)
}
