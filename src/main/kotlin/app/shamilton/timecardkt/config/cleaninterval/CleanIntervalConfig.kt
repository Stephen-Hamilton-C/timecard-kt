package app.shamilton.timecardkt.config.cleaninterval

import app.shamilton.timecardkt.config.Config

class CleanIntervalConfig : Config() {

	override val m_Name: String = "CLEAN_INTERVAL"
	override var m_Value: String
		get() = _config.clean_interval.name
		set(value) {
			_config.clean_interval = CleanInterval.valueOf(value)
			_config.saveToFile()
		}
	override val m_PossibleValues: List<String> = CleanInterval.values().map { it.name }
	override val m_Help: String = "Determines how long to keep timecard files."

}