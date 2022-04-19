package app.shamilton.adhdtimecard.core;

import com.squareup.moshi.Json

class TimeEntry(
	@Json(name="start_time") public val m_startTime: Long,
	@Json(name="end_time") public var m_endTime: Long = 0
) {
	public override fun toString(): String {
		return "TimeEntry{start_time: $m_startTime, end_time: $m_endTime}";
	}
}
