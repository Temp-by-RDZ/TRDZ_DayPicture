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

class WindowKnowledgeRecycle(private var list: MutableList<Data>, private val clickExecutor: WindowKnowledgeOnClick,): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	fun stackControl(newList: List<Data>, first: Int, count: Int) {
		this.list = newList.toMutableList()
		notifyItemRangeChanged(first,count)
	}

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

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		(holder as ListElement).myBind(list[position])
	}

	override fun getItemCount(): Int {
		return list.size
	}

	abstract inner class ListElement(view: View): RecyclerView.ViewHolder(view) {
		abstract fun myBind(data: Data)
	}

	inner class ElementCard(view: View):ListElement(view) {
		override fun myBind(data: Data) {
			(ElementKnowlageElementBinding.bind(itemView)).apply {
				title.text = data.name
				title.setOnClickListener {
					marsDescriptionTextView.visibility = View.VISIBLE}
				elemntImage.setOnClickListener {
					element.visibility = View.GONE
					marsDescriptionTextView.visibility = View.GONE}
			}
		}
	}

	inner class ElementTitle(view: View):ListElement(view)  {
		override fun myBind(data: Data) {
			(ElementKnowlageTitleBinding.bind(itemView)).apply {
				if (data.state==1) ObjectAnimator.ofFloat(blockImage, View.ROTATION, -90f, 0f).setDuration(250).start()
				title.text = data.name
				descriptionTextView.text = data.subName
				clickZone.setOnClickListener {
					if (data.state==1) ObjectAnimator.ofFloat(blockImage, View.ROTATION, 0f, -90f).setDuration(500).start()
					else ObjectAnimator.ofFloat(blockImage, View.ROTATION, -90f, 0f).setDuration(500).start()
					clickExecutor.onItemClickSpecial(data,layoutPosition) }
				root.setOnClickListener { clickExecutor.onItemClick(data,layoutPosition) }
				root.setOnLongClickListener { clickExecutor.onItemClickLong(data,layoutPosition); true }
			}
		}
	}
	inner class ElementNone(view: View):ListElement(view)  {
		override fun myBind(data: Data) {
		}
	}
}