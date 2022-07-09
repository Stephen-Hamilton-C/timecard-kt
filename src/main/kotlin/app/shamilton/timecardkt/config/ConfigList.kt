package app.shamilton.timecardkt.config

object ConfigList {
	val CONFIGS: List<Config> = listOf(
		CleanIntervalConfig(),
		TimeFormatConfig(),
		Hour24Config(),
		ColorConfig(),
	)
}