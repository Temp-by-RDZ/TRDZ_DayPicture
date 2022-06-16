package com.trdz.day_picture.w_view.segment_book

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.trdz.day_picture.databinding.ElementKnowlageElementBinding
import com.trdz.day_picture.databinding.ElementKnowlageTitleBinding

const val TYPE_EARTH = 1
const val TYPE_MARS = 2
const val TYPE_HEADER = 3

class WindowKnowledgeRecycle(private var list: MutableList<Data>, private val clickExecutor: WindowKnowledgeOnClick,): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	@SuppressLint("NotifyDataSetChanged")
	fun setList(newList: List<Data>) {
		this.list = newList.toMutableList()
		notifyDataSetChanged()
	}

	fun setAddToList(newList: List<Data>, position: Int) {
		this.list = newList.toMutableList()
		notifyItemChanged(position)
	}

	fun setRemoveToList(newList: List<Data>, position: Int) {
		this.list = newList.toMutableList()
		notifyItemRemoved(position)
	}

	override fun getItemViewType(position: Int): Int {
		return list[position].type
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return when (viewType) {
			TYPE_EARTH -> {
				val view = ElementKnowlageTitleBinding.inflate(LayoutInflater.from(parent.context))
				EarthViewHolder(view.root)
			}
			TYPE_MARS -> {
				val view = ElementKnowlageElementBinding.inflate(LayoutInflater.from(parent.context))
				MarsViewHolder(view.root)
			}
			else -> {
				val view = ElementKnowlageElementBinding.inflate(LayoutInflater.from(parent.context))
				MarsViewHolder(view.root)
			}
		}

	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		(holder as ListElement).myBind(list[position])
	}

	override fun getItemCount(): Int {
		return list.size
	}

	abstract inner class ListElement(view: View): RecyclerView.ViewHolder(view) {
		abstract fun myBind(data: Data)
	}

	inner class EarthViewHolder(view: View):ListElement(view) {
		override fun myBind(data: Data) {
			(ElementKnowlageTitleBinding.bind(itemView)).apply {
				title.text = data.someText
			}
		}
	}

	inner class MarsViewHolder(view: View):ListElement(view)  {
		override fun myBind(data: Data) {
			(ElementKnowlageElementBinding.bind(itemView)).apply {
				title.text = data.someText
				descriptionTextView.text = data.someDescription
				title.setOnClickListener { clickExecutor.onItemClickSpecial(data) }
				root.setOnClickListener { clickExecutor.onItemClick(data,position) }
				root.setOnLongClickListener { clickExecutor.onItemClickLong(data,position); true }
			}
		}
	}
}