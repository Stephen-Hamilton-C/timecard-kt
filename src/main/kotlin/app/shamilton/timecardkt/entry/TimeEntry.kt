@file:UseSerializers(LocalTimeSerializer::class)
package app.shamilton.timecardkt.entry

import app.shamilton.timecardkt.config.Configuration
import app.shamilton.timecardkt.serializer.LocalTimeSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalTime

@Serializable
data class TimeEntry(
	val startTime: LocalTime,
	var endTime: LocalTime? = null,
) {

	private val _config = Configuration.loadFromFile()

	val formattedStartTime: String
		get() = startTime.format(_config.hour_24.formatter)

	val formattedEndTime: String?
		get() = endTime?.format(_config.hour_24.formatter)

}
