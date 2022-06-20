package com.trdz.day_picture.w_view.segment_book

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trdz.day_picture.databinding.ElementKnowlageElementBinding
import com.trdz.day_picture.databinding.ElementKnowlageHiderBinding
import com.trdz.day_picture.databinding.ElementKnowlageTitleBinding
import com.trdz.day_picture.z_utility.TYPE_CARD
import com.trdz.day_picture.z_utility.TYPE_NONE
import com.trdz.day_picture.z_utility.TYPE_TITLE

class WindowKnowledgeRecycle(private var list: List<DataTheme>, private val clickExecutor: WindowKnowledgeOnClick): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private var opened = -1

	fun stackControl(newList: List<DataTheme>, first: Int, count: Int) {
		this.list = newList
		notifyItemRangeChanged(first, count)
	}

	fun subClose(position: Int) {
		notifyItemChanged(position,true)
	}

	@SuppressLint("NotifyDataSetChanged")
	fun setList(newList: List<DataTheme>) {
		this.list = newList
		notifyDataSetChanged()
	}

	fun setAddToList(newList: List<DataTheme>, position: Int) {
		this.list = newList
		notifyItemChanged(position)
	}

	fun setRemoveToList(newList: List<DataTheme>, position: Int) {
		this.list = newList
		notifyItemRemoved(position)
	}

	override fun getItemViewType(position: Int): Int {
		return when (getItemViewState(position)) {
			2 -> TYPE_NONE
			else -> list[position].type
		}
	}

	private fun getItemViewState(position: Int): Int {
		return list[position].state
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return when (viewType) {
			TYPE_CARD -> {
				val view = ElementKnowlageElementBinding.inflate(LayoutInflater.from(parent.context))
				ElementCard(view.root)
			}
			TYPE_TITLE -> {
				val view = ElementKnowlageTitleBinding.inflate(LayoutInflater.from(parent.context))
				ElementTitle(view.root)
			}
			else -> {
				val view = ElementKnowlageHiderBinding.inflate(LayoutInflater.from(parent.context))
				ElementNone(view.root)
			}
		}

	}

	override fun onBindViewHolder(
		holder: RecyclerView.ViewHolder,
		position: Int,
		payloads: MutableList<Any>
	) {
		if(payloads.isEmpty()){
			super.onBindViewHolder(holder, position, payloads)
		}else{
			(holder as ElementCard).subClose()
		}
	}
	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		(holder as ListElement).myBind(list[position])
	}

	override fun getItemCount(): Int {
		return list.size
	}

	abstract inner class ListElement(view: View): RecyclerView.ViewHolder(view) {
		abstract fun myBind(data: DataTheme)
	}

	inner class ElementCard(view: View): ListElement(view) {
		fun subClose(){
			(ElementKnowlageElementBinding.bind(itemView)).apply {
				marsDescriptionTextView.visibility = View.GONE
			}
		}
		override fun myBind(data: DataTheme) {
			(ElementKnowlageElementBinding.bind(itemView)).apply {
				root.setOnClickListener {
					if (opened > -1) subClose(opened)
					if (opened != layoutPosition) {
						opened = layoutPosition
						marsDescriptionTextView.visibility = View.VISIBLE
					}
					else opened = -1
				}
				title.text = data.name
			}
		}
	}

	inner class ElementTitle(view: View): ListElement(view) {
		override fun myBind(data: DataTheme) {
			(ElementKnowlageTitleBinding.bind(itemView)).apply {
				if (data.state == 1) ObjectAnimator.ofFloat(blockImage, View.ROTATION, -90f, 0f).setDuration(250).start()
				title.text = data.name
				descriptionTextView.text = data.subName
				clickZone.setOnClickListener {
					if (data.state == 1) ObjectAnimator.ofFloat(blockImage, View.ROTATION, 0f, -90f).setDuration(500).start()
					else ObjectAnimator.ofFloat(blockImage, View.ROTATION, -90f, 0f).setDuration(500).start()
					clickExecutor.onItemClickSpecial(data, layoutPosition)
				}
				root.setOnClickListener { clickExecutor.onItemClick(data, layoutPosition) }
				root.setOnLongClickListener { clickExecutor.onItemClickLong(data, layoutPosition); true }
			}
		}
	}

	inner class ElementNone(view: View): ListElement(view) {
		override fun myBind(data: DataTheme) {
		}
	}
}