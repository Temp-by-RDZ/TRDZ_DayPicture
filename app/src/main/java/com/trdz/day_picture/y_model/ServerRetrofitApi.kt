package com.trdz.day_picture.y_model

import com.trdz.day_picture.y_model.dto.ResponseData
import com.trdz.day_picture.z_utility.PACKAGE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerRetrofitApi {
	@GET(PACKAGE)
	fun getResponse(
		@Query("api_key") apiKey:String
	): Call<ResponseData>
}
interface ServerRetrofitCustomApi {
	@GET(PACKAGE)
	fun getResponse(
		@Query("api_key") apiKey:String,
		@Query("date") date:String
	): Call<ResponseData>
}