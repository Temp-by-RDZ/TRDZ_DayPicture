package com.trdz.day_picture.x_view_model

import com.trdz.day_picture.y_model.dto.ResponseData

sealed class StatusMessage {
	object Succsses: StatusMessage()
	object VideoError: StatusMessage()
	object SetupComplete: StatusMessage()
}