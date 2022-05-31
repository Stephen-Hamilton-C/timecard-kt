package app.shamilton.timecardkt.config.hour24

import java.time.format.DateTimeFormatter

enum class Hour24(val formatter: DateTimeFormatter) {
	TRUE(DateTimeFormatter.ofPattern("HH:mm")),
	FALSE(DateTimeFormatter.ofPattern("h:mm a")),
}