package com.trdz.day_picture.x_view_model

import com.trdz.day_picture.y_model.ServerStatus

interface ServerResponse {
	fun success(data: ServerStatus)
	fun fail(code: Int)
}