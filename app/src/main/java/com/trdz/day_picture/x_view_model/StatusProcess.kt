package com.trdz.day_picture.x_view_model

import com.trdz.day_picture.y_model.dto.ResponseData

sealed class StatusProcess {
	object Load : StatusProcess()
	data class Success(val data: ResponseData) : StatusProcess()
	data class Video(val data: ResponseData) : StatusProcess()
	data class Error(val code: Int, val error: Throwable) : StatusProcess()
}