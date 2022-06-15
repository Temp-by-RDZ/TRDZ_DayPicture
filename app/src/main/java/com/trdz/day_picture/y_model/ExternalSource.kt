package com.trdz.day_picture.y_model

interface ExternalSource {
	fun load(date: String?):ServersResult
}