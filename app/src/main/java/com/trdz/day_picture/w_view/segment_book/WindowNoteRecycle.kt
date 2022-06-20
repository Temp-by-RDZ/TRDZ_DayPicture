package com.trdz.day_picture.w_view.segment_book

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.ElementNoteLinetBinding

class WindowNoteRecycle(private val clickExecutor: WindowNoteOnClick): RecyclerView.Adapter<WindowNoteRecycle.NoteLine?>(), WindowNoteOnTouch {

	private lateinit var list: List<String>

	@SuppressLint("NotifyDataSetChanged")
	fun setList(newList: List<String>) {
		this.list = newList
		notifyDataSetChanged()
	}

	fun setAddToList(newList: List<String>, position: Int) {
		this.list = newList
		notifyItemChanged(position)
	}

	fun setRemoveFromList(newList: List<String>, position: Int) {
		this.list = newList
		notifyItemRemoved(position)
	}

	fun setMoveInList(newList: List<String>, fromPosition: Int, toPosition: Int) {
		this.list = newList
		notifyItemMoved(fromPosition, toPosition)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteLine {
		val view = ElementNoteLinetBinding.inflate(LayoutInflater.from(parent.context),parent,false)
		return NoteLine(view.root)
	}

	override fun onBindViewHolder(holder: NoteLine, position: Int) {
		holder.bind(list[position])
	}

	inner class NoteLine(view: View): RecyclerView.ViewHolder(view), WindowNoteOnTouchHelp  {
		fun bind(data: String) {
			(ElementNoteLinetBinding.bind(itemView)).apply {
				lName.text = data
			}
		}
		override fun onItemSelected() {
			itemView.setBackgroundColor(Color.LTGRAY)
		}

		override fun onItemClear() {
			itemView.setBackgroundColor(Color.WHITE)
		}
	}

	override fun onItemMove(fromPosition: Int, toPosition: Int) {
		clickExecutor.onItemMove(fromPosition,toPosition)
	}

	override fun onItemDismiss(position: Int) {
		clickExecutor.onItemRemove(position)
	}

}