package app.shamilton.timecardkt.config.timeformat

import app.shamilton.timecardkt.config.Config

class TimeFormatConfig : Config() {

	override val m_Name: String = "TIME_FORMAT"
	override var m_Value: String
		get() = _config.time_format.name
		set(value) {
			_config.time_format = TimeFormat.valueOf(value)
			_config.saveToFile()
		}

	override val m_PossibleValues: List<String> = TimeFormat.values().map { it.name }
	override val m_Help: String = "Determines how time worked and time on break is displayed."

}