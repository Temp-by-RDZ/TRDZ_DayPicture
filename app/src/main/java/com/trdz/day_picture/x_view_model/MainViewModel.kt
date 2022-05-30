package com.trdz.day_picture.x_view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.trdz.day_picture.y_model.*
import java.lang.StringBuilder
import java.util.*

class MainViewModel(
	private val dataLive: SingleLiveData<StatusProcess> = SingleLiveData(),
	private val repository: DataExecutor = DataExecutor(),
) : ViewModel(), ServerResponse {

	fun getData(): LiveData<StatusProcess> {
		return dataLive
	}

	fun analyze(){
		val sb = StringBuilder()
		with(sb) {
			append(Calendar.getInstance().get(Calendar.YEAR))
			append("-")
			(Calendar.getInstance().get(Calendar.MONTH)+1).apply {
				if (this < 10) append("0")
				append(this)
			}
			append("-")
			(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1).apply {
				if (this < 10) append("0")
				append(this)
			}
		}
		Log.d("@@@", "Mod - get date: $sb")
		start(sb.toString())
	}

	fun start(date: String? = null) {
		repository.connection(this@MainViewModel, date)
	}

	override fun success(data: ServerStatus) {
		Log.d("@@@", "Mod - get success answer")
		dataLive.postValue(StatusProcess.Success(data.result!!))
	}

	override fun fail(code: Int) {
		Log.d("@@@", "Mod - request failed $code")
		dataLive.postValue(StatusProcess.Error(code,IllegalAccessError()))
	}
}