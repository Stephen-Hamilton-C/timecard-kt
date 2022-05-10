package app.shamilton.timecard.core;

import com.squareup.moshi.Json
import java.time.LocalTime

data class TimeEntry(
	@Json(name="start_time")
	val startTime: LocalTime,
	@Json(name="end_time")
	var endTime: LocalTime? = null,
) {}
