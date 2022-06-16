package com.trdz.day_picture.w_view.segment_book

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.trdz.day_picture.R
import com.trdz.day_picture.databinding.FragmentWindowPoeBinding
import com.trdz.day_picture.w_view.Leader
import com.trdz.day_picture.w_view.MainActivity

class WindowKnowledge: Fragment(), WindowKnowledgeOnClick {

	private var _binding: FragmentWindowPoeBinding? = null
	private val binding get() = _binding!!
	private var _executors: Leader? = null
	private val executors get() = _executors!!

	private val list = arrayListOf(
		Data("Earth", "Earth des", TYPE_EARTH),
		Data("Earth", "Earth des", TYPE_EARTH),
		Data("Mars", "Mars des", TYPE_MARS),
		Data("Earth", "Earth des", TYPE_EARTH),
		Data("Earth", "Earth des", TYPE_EARTH),
		Data("Earth", "Earth des", TYPE_EARTH),
		Data("Mars", "Mars des", TYPE_MARS)
	)
	private val adapter = WindowKnowledgeRecycle(list, this)

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowPoeBinding.inflate(inflater, container, false)
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

	override fun onItemClick(data: Data,position: Int) {
		executors.getExecutor().showToast(requireContext(), "Детали временно недоступны.\nЗажмите длә изменениә",Toast.LENGTH_LONG)
	}

	override fun onItemClickLong(data: Data,position: Int) {

		activity?.let {
			AlertDialog.Builder(it)
				.setTitle("Управление деррикторией LONG:")
				.setMessage(" ${data.someText}")
				.setPositiveButton("Удалить") { _, _ ->
					list.removeAt(position)
					adapter.setRemoveToList(list,position)
				}
				.setNegativeButton("Добавитғ") { _, _ ->
					list.add(Data("Earth", "Earth des", TYPE_EARTH))
					adapter.setAddToList(list,list.size)
				}
				.setNeutralButton("Узнать больше") { dialog, _ -> dialog.dismiss()
				}
				.create()
				.show()
		}
	}

	override fun onItemClickSpecial(data: Data) {

		activity?.let {
			AlertDialog.Builder(it)
				.setTitle("Управление деррикторией SPEC:")
				.setMessage(" ${data.someText}")
				.setPositiveButton("ага") { _, _ -> }
				.setNegativeButton("ага") { _, _ -> }
				.setNeutralButton("Узнать больше") { dialog, _ -> dialog.dismiss() }
				.create()
				.show()
		}
	}

	companion object {
		@JvmStatic
		fun newInstance() = WindowKnowledge()
	}


}
