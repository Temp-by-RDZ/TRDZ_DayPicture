package com.trdz.day_picture.x_view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.day_picture.y_model.*
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
	private val dataLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val messageLive: SingleLiveData<StatusMessage> = SingleLiveData(),
	private val repository: DataExecutor = DataExecutor(),
): ViewModel(), ServerResponse {

	private var page: Int = 1

	fun changePage(goTo: Int) {
		if (page < 0) {
			page = 0
			return
		}
		else {
			page = goTo
			pageObserve()
		}
	}

	private fun pageObserve() {
		when (page) {
			1 -> messageLive.postValue(StatusMessage.SetupComplete)
			2 -> page = 1
		}
	}

	fun getData(): LiveData<StatusProcess> {
		return dataLive
	}

	fun getMessage(): LiveData<StatusMessage> {
		return messageLive
	}

	fun analyze() {
		val calendar = Calendar.getInstance()
		calendar.add(Calendar.DATE, -1)
		val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
		val date: String = dateFormat.format(calendar.time)
		Log.d("@@@", "Mod - get prev date: $date")
		start(date)
	}

	fun start(date: String? = null) {
		dataLive.postValue(StatusProcess.Load)
		repository.connection(this@MainViewModel, date)
		messageLive.postValue(StatusMessage.Succsses)
	}

	override fun success(data: ServersResult) {
		Log.d("@@@", "Mod - get success answer")
		if (data.type == "video") {
			dataLive.postValue(StatusProcess.Video(data))
			messageLive.postValue(StatusMessage.VideoError)
		}
		else {
			dataLive.postValue(StatusProcess.Success(data))
		}
	}

	override fun fail(code: Int) {
		Log.d("@@@", "Mod - request failed $code")
		dataLive.postValue(StatusProcess.Error(code, IllegalAccessError()))
	}
}