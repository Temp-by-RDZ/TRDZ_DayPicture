package com.trdz.day_picture.x_view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.day_picture.y_model.*
import com.trdz.day_picture.z_utility.PREFIX_MRP
import com.trdz.day_picture.z_utility.PREFIX_POD
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
	private val dataPodLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val dataPomLive: SingleLiveData<StatusProcess> = SingleLiveData(),
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

	fun getPomData(): LiveData<StatusProcess> = dataPomLive
	fun getPodData(): LiveData<StatusProcess> = dataPodLive
	fun getMessage(): LiveData<StatusMessage> = messageLive

	fun initialize(prefix: String) {
		messageLive.postValue(StatusMessage.Success)
		when (prefix) {
			PREFIX_MRP -> analyze(prefix, 0)
			PREFIX_POD -> startPod()
		}
	}

	private fun getData(change: Int): String {
		val calendar = Calendar.getInstance()
		calendar.add(Calendar.DATE, change)
		val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
		return dateFormat.format(calendar.time)
	}

	fun analyze(prefix: String, change: Int = 0) {
		when (prefix) {
			PREFIX_POD -> startPod(getData(change))
			PREFIX_MRP -> startMrp(getData(change))
		}
	}

	private fun startPod(date: String? = null) {
		dataPodLive.postValue(StatusProcess.Load)
		repository.connection(this@MainViewModel, PREFIX_POD, date)

	}

	private fun startMrp(date: String? = null) {
		dataPomLive.postValue(StatusProcess.Load)
		repository.connection(this@MainViewModel, PREFIX_MRP, date)
	}

	override fun success(prefix: String, data: ServersResult) {
		Log.d("@@@", "Mod - get success $prefix answer")
		when (prefix) {
			PREFIX_POD -> {
				if (data.type == "video") {
					dataPodLive.postValue(StatusProcess.Video(data))
					messageLive.postValue(StatusMessage.VideoError)
				}
				else {
					dataPodLive.postValue(StatusProcess.Success(data))
				}
			}
			PREFIX_MRP -> {
				if (data.type == "video") {
					dataPomLive.postValue(StatusProcess.Video(data))
					messageLive.postValue(StatusMessage.VideoError)
				}
				else {
					dataPomLive.postValue(StatusProcess.Success(data))
				}
			}
		}
	}

	override fun fail(prefix: String, code: Int) {
		Log.d("@@@", "Mod - request $prefix failed $code")
		when (prefix) {
			PREFIX_POD -> dataPodLive.postValue(StatusProcess.Error(code, IllegalAccessError()))
			PREFIX_MRP -> dataPomLive.postValue(StatusProcess.Error(code, IllegalAccessError()))
		}
	}
}