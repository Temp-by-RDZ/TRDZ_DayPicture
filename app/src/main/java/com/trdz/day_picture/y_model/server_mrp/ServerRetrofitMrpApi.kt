package com.trdz.day_picture.y_model.server_mrp

import com.trdz.day_picture.y_model.server_mrp.dto.ResponseDataMRP
import com.trdz.day_picture.y_model.server_pod.dto.ResponseDataPOD
import com.trdz.day_picture.z_utility.PACKAGE_MRP
import com.trdz.day_picture.z_utility.PACKAGE_POD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerRetrofitMrpCustomApi {
	@GET(PACKAGE_MRP)
	fun getResponse(
		@Query("earth_date") date:String,
		@Query("api_key") apiKey:String,
	): Call<ResponseDataMRP>
}