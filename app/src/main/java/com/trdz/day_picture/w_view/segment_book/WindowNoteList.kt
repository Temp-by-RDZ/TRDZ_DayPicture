package com.trdz.day_picture.w_view.segment_book

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.trdz.day_picture.w_view.Leader
import com.trdz.day_picture.w_view.MainActivity
import com.trdz.day_picture.databinding.FragmentWindowNoteListBinding
import java.util.ArrayList

class WindowNoteList: Fragment(), WindowNoteOnClick {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowNoteListBinding? = null
	private val binding get() = _binding!!
	private val duration = 2000L
	private var isOpen = false
	//endregion

	private val list = ArrayList<DataLine>()
	private val adapter = WindowNoteRecycle(this)

	//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowNoteListBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		ItemTouchHelper(WindowNoteTouch(adapter)).attachToRecyclerView(binding.recyclerView)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.recyclerView.adapter = adapter
		buttonBinds()
		initialize()
	}

	//endregion

	//region Main functional
	private fun buttonBinds() {
		binding.plusImageview.setOnClickListener {
			if (isOpen) addingClose()
			else addingOpen()
			isOpen = !isOpen
		}
	}

	private fun addingOpen() {

		ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 0f, 360f).setDuration(duration).start()
		ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -130f).setDuration(duration).start()
		ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -260f).setDuration(duration).start()
		binding.optionOneContainer.animate().alpha(1f).setDuration(duration).setListener(object: AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				super.onAnimationEnd(animation)
				binding.optionOneContainer.isClickable = true
			}
		})
		binding.optionTwoContainer.animate().alpha(1f).setDuration(duration).setListener(object: AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				super.onAnimationEnd(animation)
				binding.optionTwoContainer.isClickable = true
			}
		})
	}

	private fun addingClose() {

		ObjectAnimator.ofFloat(binding.plusImageview, View.ROTATION, 360f, 0f).setDuration(duration).start()
		ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f).setDuration(duration).start()
		ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f).setDuration(duration).start()
		binding.optionOneContainer.animate().alpha(0f).setDuration(duration).setListener(object: AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				super.onAnimationEnd(animation)
				binding.optionOneContainer.isClickable = false
			}
		})
		binding.optionTwoContainer.animate().alpha(0f).setDuration(duration).setListener(object: AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				super.onAnimationEnd(animation)
				binding.optionTwoContainer.isClickable = false
			}
		})
	}

	private fun initialize() {
		for (i in 0..99) {
			list.add(DataLine(i,"Заметка ${i+1}"))
		}
		adapter.setList(list)
	}

	//endregion

	override fun onItemClick(data: DataLine, position: Int) {
		//list[position].name=("description")
		//adapter.setChangeInList(list.map { it })
		changeAdapterData(position)
	}
	private fun changeAdapterData(position: Int) {
		adapter.setChangeInList(createItemList(position).map { it })
	}

	private fun createItemList(position: Int): List<DataLine> {
		val newList = ArrayList<DataLine>()
		list.forEachIndexed() { index, element ->
			if (position==index) newList.add(DataLine(0, "Header"))
			else newList.add(element)
		}
		return newList
		}

	override fun onItemClickLong(data: DataLine, position: Int) {
		list[position].description=(list[position].name+" description")
		adapter.setChangeInList(list)
	}

	override fun onItemMove(fromPosition: Int, toPosition: Int) {
		list.removeAt(fromPosition).apply {
			list.add(toPosition, this)
		}
		adapter.setMoveInList(list,fromPosition,toPosition)
	}

	override fun onItemRemove(position: Int) {
		list.removeAt(position)
		adapter.setRemoveFromList(list,position)
	}

	companion object {
		fun newInstance() = WindowNoteList()
	}
}