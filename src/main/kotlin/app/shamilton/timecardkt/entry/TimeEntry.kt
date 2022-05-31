@file:UseSerializers(LocalTimeSerializer::class)
package app.shamilton.timecardkt.entry

import app.shamilton.timecardkt.serializer.LocalTimeSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalTime

@Serializable
data class TimeEntry(
	val startTime: LocalTime,
	var endTime: LocalTime? = null,
) {}
