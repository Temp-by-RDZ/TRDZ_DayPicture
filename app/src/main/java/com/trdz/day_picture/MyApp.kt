package com.trdz.day_picture

import android.app.Application
import com.google.gson.GsonBuilder
import com.trdz.day_picture.y_model.ServerRetrofitApi
import com.trdz.day_picture.y_model.ServerRetrofitCustomApi
import com.trdz.day_picture.z_utility.DOMAIN
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp: Application() {

	companion object {
		private var retrofit: ServerRetrofitApi? = null
		private var retrofitCustom: ServerRetrofitCustomApi? = null

		private fun createRetrofit() {
			retrofit = Retrofit.Builder().apply {
				baseUrl(DOMAIN)
				addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
			}.build().create(ServerRetrofitApi::class.java)
		}

		fun getRetrofit(): ServerRetrofitApi {
			if (retrofit == null) createRetrofit()
			return retrofit!!
		}
		private fun createRetrofitCustom() {
			retrofitCustom = Retrofit.Builder().apply {
				baseUrl(DOMAIN)
				addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
			}.build().create(ServerRetrofitCustomApi::class.java)
		}

		fun getRetrofitCustom(): ServerRetrofitCustomApi {
			if (retrofitCustom == null) createRetrofitCustom()
			return retrofitCustom!!
		}
	}
}