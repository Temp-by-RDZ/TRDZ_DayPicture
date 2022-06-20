package com.trdz.day_picture.w_view.segment_book

data class Change<out T>(val oldData: T, val newData: T)

fun <T> createCombinedPayload(payloads: List<Change<T>>): Change<T> {
	assert(payloads.isNotEmpty())
	val firstChange = payloads.first()
	val lastChange = payloads.last()

	return Change(firstChange.oldData, lastChange.newData)
}