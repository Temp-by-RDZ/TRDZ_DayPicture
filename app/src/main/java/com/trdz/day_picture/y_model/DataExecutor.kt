package com.trdz.day_picture.y_model

import android.util.Log
import com.trdz.day_picture.x_view_model.ServerResponse
import com.trdz.day_picture.y_model.server_epc.ServerReceiverEPC
import com.trdz.day_picture.y_model.server_mrp.ServerRetrofitMRP
import com.trdz.day_picture.y_model.server_pod.ServerRetrofitPOD
import com.trdz.day_picture.z_utility.PREFIX_EPC
import com.trdz.day_picture.z_utility.PREFIX_MRP
import com.trdz.day_picture.z_utility.PREFIX_POD

class DataExecutor: Repository {

	override fun connection(serverListener: ServerResponse, prefix: String, date: String?) {
		Log.d("@@@", "Rep - start connection $prefix on date: $date")
		lateinit var externalSource: ExternalSource
		when (prefix) {
			PREFIX_POD -> externalSource = ServerRetrofitPOD()
			PREFIX_MRP -> externalSource = ServerRetrofitMRP()
			PREFIX_EPC -> externalSource = ServerReceiverEPC()
		}
		Thread {
			val result = externalSource.load(date)
			if (result.code in 200..299 ) serverListener.success(prefix,result)
			else serverListener.fail(prefix, result.code)
		}.start()
	}

}
