package app.shamilton.timecard.core;

import com.squareup.moshi.Json
import java.time.LocalTime

data class TimeEntry(
	@Json(name="start_time")
	public val m_StartTime: LocalTime,
	@Json(name="end_time")
	public var m_EndTime: LocalTime? = null,
) {}
