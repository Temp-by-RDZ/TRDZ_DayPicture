package com.trdz.day_picture.y_model.server_mrp

import android.util.Log
import com.trdz.day_picture.BuildConfig
import com.trdz.day_picture.MyApp
import com.trdz.day_picture.y_model.ExternalSource
import com.trdz.day_picture.y_model.ServersResult
import com.trdz.day_picture.y_model.server_mrp.dto.ResponseDataMRP
import retrofit2.Response

class ServerRetrofitMRP: ExternalSource {

	override fun load(date: String?): ServersResult {
		val retrofit = MyApp.getRetrofitMrp()

		return try {
			val response = retrofit.getResponse(date!!,BuildConfig.NASA_API_KEY).execute()
			responseFormation(response)
		}
		catch (Ignored: Exception) {
			responseFail()
		}
	}

	private fun responseFormation(response: Response<ResponseDataMRP>) : ServersResult {
		return if (response.isSuccessful) response.body()!!.run {
			if (photos.isEmpty()) return@run ServersResult(-2)
			val picture = photos.random()
			ServersResult(response.code(), picture.rover.name, picture.rover.status, picture.img_src, picture.camera.name)
		}
		else ServersResult(response.code())
	}

	private fun responseFail() : ServersResult {
		Log.d("@@@", "Ser - MRP Connection Error")
		return ServersResult(-1)
	}
}
