package com.trdz.day_picture.x_view_model

import com.trdz.day_picture.y_model.ServersResult
import com.trdz.day_picture.y_model.dtoPOD.ResponseDataPOD

sealed class StatusProcess {
	object Load : StatusProcess()
	data class Success(val dataPOD: ServersResult) : StatusProcess()
	data class Video(val dataPOD: ServersResult) : StatusProcess()
	data class Error(val code: Int, val error: Throwable) : StatusProcess()
}