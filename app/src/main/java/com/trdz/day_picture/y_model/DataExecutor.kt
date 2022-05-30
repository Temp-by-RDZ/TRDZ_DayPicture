package com.trdz.day_picture.y_model

import android.util.Log
import com.trdz.day_picture.x_view_model.ServerResponse

class DataExecutor: Repository {

	private val externalSource: ExternalSource = ServerRetrofit()

	override fun connection(serverListener: ServerResponse, date: String?) {
		Log.d("@@@", "Rep - start connection "+date)
		Thread {
			val result = externalSource.load(date)
			if (result.result != null ) serverListener.success(result)
			else serverListener.fail(result.code)
		}.start()
	}

}
