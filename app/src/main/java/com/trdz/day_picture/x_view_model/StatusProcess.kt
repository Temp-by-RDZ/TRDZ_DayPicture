package com.trdz.day_picture.x_view_model

import com.trdz.day_picture.y_model.ServersResult

sealed class StatusProcess {
	object Load : StatusProcess()
	data class Saving(val data: String) : StatusProcess()
	data class Success(val data: ServersResult) : StatusProcess()
	data class Video(val data: ServersResult) : StatusProcess()
	data class Error(val code: Int, val error: Throwable) : StatusProcess()
}