package app.shamilton.timecardkt.config

import java.time.format.DateTimeFormatter

enum class Hour24(val formatter: DateTimeFormatter) {
	TRUE(DateTimeFormatter.ofPattern("HH:mm")),
	FALSE(DateTimeFormatter.ofPattern("h:mm a")),
}

class Hour24Config : Config() {

	override val m_Name: String = "24_HOUR"
	override var m_Value: String
		get() = _config.hour_24.toString().uppercase()
		set(value) {
			_config.hour_24 = Hour24.valueOf(value)
			_config.saveToFile()
		}

	override val m_PossibleValues: List<String> = Hour24.values().map { it.name }
	override val m_Help: String = "Determines whether or not to show time in 24-hour format."

}