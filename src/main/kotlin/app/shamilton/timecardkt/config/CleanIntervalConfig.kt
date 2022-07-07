package app.shamilton.timecardkt.config

enum class CleanInterval(val days: Int) {
	MANUALLY(1),
	DAILY(1),
	WEEKLY(7),
	MONTHLY(30),
}

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