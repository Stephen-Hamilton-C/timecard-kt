package app.shamilton.timecard.config

enum class TimeFormat {
	ISO, //17:31
	QUARTER_HOUR, //17.5 hours
	WRITTEN, // 17 hours and 31 minutes
	WRITTEN_SHORT, // 17 hrs, 31 min
}

class TimeFormatConfig : Config() {

	override val m_Name: String = "TIME_FORMAT"
	override var m_Value: String
		get() = _config.time_format.name
		set(value) {
			_config.time_format = TimeFormat.valueOf(value)
			_config.saveToFile()
		}

	override val m_PossibleValues: List<String> = TimeFormat.values().map { it.name }

}