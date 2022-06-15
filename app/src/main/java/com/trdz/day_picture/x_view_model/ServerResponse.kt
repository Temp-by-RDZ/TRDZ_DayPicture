package com.trdz.day_picture.x_view_model

import com.trdz.day_picture.y_model.ServersResult

interface ServerResponse {
	fun success(data: ServersResult)
	fun fail(code: Int)
}