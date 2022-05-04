package app.shamilton.adhdtimecard.core;

import com.squareup.moshi.Json
import java.time.LocalDateTime
import java.time.LocalTime

data class TimeEntry(
	@Json(name="start_time")
	public val m_startTime: LocalTime,
	@Json(name="end_time")
	public var m_endTime: LocalTime? = null,
) {}
