package app.shamilton.timecardkt.config

import app.shamilton.timecardkt.config.cleaninterval.CleanIntervalConfig
import app.shamilton.timecardkt.config.hour24.Hour24Config
import app.shamilton.timecardkt.config.timeformat.TimeFormatConfig

object ConfigList {
	val CONFIGS: List<Config> = listOf(
		CleanIntervalConfig(),
		TimeFormatConfig(),
		Hour24Config(),
	)
}