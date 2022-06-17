package com.trdz.day_picture.w_view.segment_book

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.FragmentWindowKnowlageListBinding
import com.trdz.day_picture.w_view.Leader
import com.trdz.day_picture.w_view.MainActivity
import com.trdz.day_picture.z_utility.TYPE_CARD
import com.trdz.day_picture.z_utility.TYPE_TITLE

class WindowKnowledgeList: Fragment(), WindowKnowledgeOnClick {

	private var _binding: FragmentWindowKnowlageListBinding? = null
	private val binding get() = _binding!!
	private var _executors: Leader? = null
	private val executors get() = _executors!!

	private val list = arrayListOf(
		Data("Заголовок 1", "Две темы", TYPE_TITLE, 1),
		Data("Тема 1", "Тема 1", TYPE_CARD, 2),
		Data("Тема 2", "Тема 2", TYPE_CARD, 2),
		Data("Заголовок 2", "Три темы", TYPE_TITLE, 3),
		Data("Тема 1", "Тема 1", TYPE_CARD, 4),
		Data("Тема 2", "Тема 2", TYPE_CARD, 4),
		Data("Тема 3", "Тема 3", TYPE_CARD, 4),
		Data("Заголовок 3", "Одна тема", TYPE_TITLE, 5, 1),
		Data("Тема 1", "Тема 1", TYPE_CARD, 6,2),
	)
	private val adapter = WindowKnowledgeRecycle(list, this)

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowKnowlageListBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		return binding.root
	}

	@RequiresApi(Build.VERSION_CODES.P)
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			binding.recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
				binding.naming.isSelected = binding.recyclerView.canScrollVertically(-1)
			}
		}


		binding.recyclerView.adapter = adapter
	}


	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.menu_bottom_bar, menu)
	}

	override fun onItemClick(data: Data, position: Int) {
		executors.getExecutor().showToast(requireContext(), "Детали временно недоступны.\nЗажмите для изменения\nНажмите на икинку что бы скрыть подпункты", Toast.LENGTH_LONG)
	}

	override fun onItemClickLong(data: Data, position: Int) {

		activity?.let {
			AlertDialog.Builder(it)
				.setTitle("Управление деррикторией LONG:")
				.setMessage(" ${data.name}")
				.setPositiveButton("Удалить") { _, _ ->
					list.removeAt(position)
					adapter.setRemoveToList(list, position)
				}
				.setNegativeButton("Добавить") { _, _ ->
					list.add(Data("Earth", "Earth des", TYPE_TITLE))
					adapter.setAddToList(list, list.size)
				}
				.setNeutralButton("Узнать больше") { dialog, _ ->
					dialog.dismiss()
				}
				.create()
				.show()
		}
	}

	override fun onItemClickSpecial(data: Data, position: Int) {
		var count = 0
		if (data.state==1) {
			list[position].state = 0
			count = setState(0,data.group+1,position)
		}
		else {
			list[position].state = 1
			count = setState(2,data.group+1,position)
		}
		adapter.stackControl(list, position+1,count)
	}
	private fun setState(state: Int,group: Int,position: Int): Int {
		var count = 0
		list.forEach { tek ->
			if (tek.group == group) {
				tek.state = state
				count++
			}
		}
		return count
	}

	companion object {
		@JvmStatic
		fun newInstance() = WindowKnowledgeList()
	}


}
