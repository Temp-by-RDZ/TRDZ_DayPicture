package com.trdz.day_picture.x_view_model

sealed class StatusMessage {
	object Success: StatusMessage()
	object VideoError: StatusMessage()
	object SetupComplete: StatusMessage()
}