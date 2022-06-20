package com.trdz.day_picture.w_view.segment_book

import android.util.Log
import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(
	private val oldList: List<DataLine>,
	private val newList: List<DataLine>
) : DiffUtil.Callback() {
	override fun getOldListSize() = oldList.size

	override fun getNewListSize() = newList.size


	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return oldList[oldItemPosition].id == newList[newItemPosition].id
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		val st = (oldList[oldItemPosition].name == newList[newItemPosition].name)
				&& ((oldList[oldItemPosition].description == newList[newItemPosition].description))

		if (!st) Log.d("@@@T", "get "+newItemPosition)
		return st
	}

	override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
		val oldItem = oldList[oldItemPosition]
		val newItem = newList[newItemPosition]
		return Change(oldItem,newItem)
	}
}