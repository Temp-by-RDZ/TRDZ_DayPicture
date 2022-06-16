package com.trdz.day_picture.y_model

import com.trdz.day_picture.x_view_model.ServerResponse

interface Repository {
	fun connection(serverListener: ServerResponse, prefix: String, date: String?)
}