package com.trdz.day_picture.y_model.server_epc

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.trdz.day_picture.BuildConfig
import com.trdz.day_picture.y_model.ExternalSource
import com.trdz.day_picture.y_model.ServersResult
import com.trdz.day_picture.y_model.server_epc.dto.ResponseDataEPC
import com.trdz.day_picture.z_utility.DOMAIN
import com.trdz.day_picture.z_utility.PACKAGE_EPA
import com.trdz.day_picture.z_utility.PACKAGE_EPC
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.text.StringBuilder

class ServerReceiverEPC(): ExternalSource {

	override fun load(date: String?): ServersResult {
		var responseCode = -1

		val uri = URL(StringBuilder("").apply {
			append(DOMAIN)
			append(PACKAGE_EPC)
			append(date)
			append("?api_key=")
			append(BuildConfig.NASA_API_KEY)
		}.toString())

		val urlConnection: HttpsURLConnection = (uri.openConnection() as HttpsURLConnection).apply {
			connectTimeout = 1000
			readTimeout = 1000
		//addRequestProperty(API_KEY, BuildConfig.WEATHER_API_KEY)
		}

		try { responseCode = urlConnection.responseCode }
		catch (Ignored: Exception) {
			Log.d("@@@", "Ser- Connection Error")
			return ServersResult(responseCode)
		}

		try {
			if (responseCode in 1..299) {

				val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
				val responseData: ResponseDataEPC = Gson().fromJson(buffer, ResponseDataEPC::class.java)
				if (responseData.isEmpty()) return ServersResult(-2)
				val result = responseData.random()
				val imageEPA = StringBuilder().apply {
					append(DOMAIN)
					append(PACKAGE_EPA)
					append(date!!.replace("-","/"))
					append("/png/")
					append(result.image)
					append(".png?api_key=")
					append(BuildConfig.NASA_API_KEY)
				}
				return ServersResult(responseCode, "Earth"+result.centroid_coordinates,result.caption, imageEPA.toString(),"EPA_Image")
			}
		}
		catch (Ignored: JsonSyntaxException) {
			return ServersResult(responseCode)
		}
		finally {
			urlConnection.disconnect()
		}
		return ServersResult(responseCode)

	}
}