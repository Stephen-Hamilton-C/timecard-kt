package app.shamilton.timecard.config

import app.shamilton.timecard.config.cleaninterval.CleanIntervalConfig
import app.shamilton.timecard.config.timeformat.TimeFormatConfig

object ConfigList {
	val CONFIGS: List<Config> = listOf(
		CleanIntervalConfig(),
		TimeFormatConfig(),
	)
}