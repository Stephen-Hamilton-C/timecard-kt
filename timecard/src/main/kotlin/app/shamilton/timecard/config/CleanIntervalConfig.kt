package app.shamilton.timecard.config

class CleanIntervalConfig : Config() {

	override val m_Name: String = "clean_interval"
	override var m_Value: String = CleanInterval.WEEKLY.name
	override val m_PossibleValues: List<String> = listOf(
		CleanInterval.MANUALLY.name,
		CleanInterval.DAILY.name,
		CleanInterval.WEEKLY.name,
		CleanInterval.MONTHLY.name,
	)

}