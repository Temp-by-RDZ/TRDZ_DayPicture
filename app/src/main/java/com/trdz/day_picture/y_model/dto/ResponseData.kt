package com.trdz.day_picture.y_model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseData(
	val date: String,
	val explanation: String,
	val hdurl: String,
	@SerializedName("media_type")
	val mediaType: String,
	@SerializedName("service_version")
	val serviceVersion: String,
	val title: String,
	val url: String
	): Parcelable
