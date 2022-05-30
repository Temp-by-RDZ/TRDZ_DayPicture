package com.trdz.day_picture.y_model

import android.util.Log
import com.trdz.day_picture.BuildConfig
import com.trdz.day_picture.MyApp

class ServerRetrofit: ExternalSource {

	override fun load(date: String?): ServerStatus {
		if (date!=null) return loadCustom(date)
		val retrofit = MyApp.getRetrofit()

		return try {
			val response = retrofit.getResponse(BuildConfig.NASA_API_KEY).execute()
			if (response.isSuccessful) ServerStatus(response.code(), response.body())
			else ServerStatus(response.code())
		}
		catch (Ignored: Exception) {
			Log.d("@@@", "Ser - Connection Error")
			ServerStatus(-1)
		}
	}

	private fun loadCustom(date: String): ServerStatus {
		val retrofit = MyApp.getRetrofitCustom()

		return try {
			val response = retrofit.getResponse(BuildConfig.NASA_API_KEY,date).execute()
			if (response.isSuccessful) ServerStatus(response.code(), response.body())
			else ServerStatus(response.code())
		}
		catch (Ignored: Exception) {
			Log.d("@@@", "Ser - Connection Error")
			ServerStatus(-1)
		}
	}

}
