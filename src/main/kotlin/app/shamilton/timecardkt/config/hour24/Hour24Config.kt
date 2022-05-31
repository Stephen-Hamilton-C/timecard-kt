package app.shamilton.timecardkt.config.hour24

import app.shamilton.timecardkt.config.Config

class Hour24Config : Config() {

	override val m_Name: String = "24_HOUR"
	override var m_Value: String
		get() = _config.hour_24.toString().uppercase()
		set(value) {
			_config.hour_24 = Hour24.valueOf(value)
			_config.saveToFile()
		}

	override val m_PossibleValues: List<String> = Hour24.values().map { it.name }

}