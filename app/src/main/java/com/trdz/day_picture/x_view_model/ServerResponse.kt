package com.trdz.day_picture.x_view_model

import com.trdz.day_picture.y_model.ServersResult

interface ServerResponse {
	fun success(prefix: String, data: ServersResult)
	fun fail(prefix: String, code: Int)
}