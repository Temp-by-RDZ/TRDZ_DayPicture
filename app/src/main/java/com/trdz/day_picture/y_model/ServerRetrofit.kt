package com.trdz.day_picture.y_model

import android.util.Log
import com.trdz.day_picture.BuildConfig
import com.trdz.day_picture.MyApp
import com.trdz.day_picture.x_view_model.ServerResponse
import com.trdz.day_picture.y_model.dtoPOD.ResponseDataPOD
import retrofit2.Response

class ServerRetrofit: ExternalSource {

	override fun load(date: String?): ServersResult {
		if (date!=null) return loadCustom(date)
		val retrofit = MyApp.getRetrofit()

		return try {
			val response = retrofit.getResponse(BuildConfig.NASA_API_KEY).execute()
			responseFormation(response)
		}
		catch (Ignored: Exception) {
			responseFail()
		}
	}

	private fun loadCustom(date: String): ServersResult {
		val retrofit = MyApp.getRetrofitCustom()

		return try {
			val response = retrofit.getResponse(BuildConfig.NASA_API_KEY,date).execute()
			responseFormation(response)
		}
		catch (Ignored: Exception) {
			responseFail()
		}
	}

	private fun responseFormation(response: Response<ResponseDataPOD>) :ServersResult {
		return if (response.isSuccessful) response.body()!!.run {
			ServersResult(response.code(), title, explanation, url, mediaType)
		}
		else ServersResult(response.code(), "NO FILE", "Missing information.")
	}

	private fun responseFail() :ServersResult {
		Log.d("@@@", "Ser - Connection Error")
		return ServersResult(-1, "NO FILE", "Missing information.")
	}
}
