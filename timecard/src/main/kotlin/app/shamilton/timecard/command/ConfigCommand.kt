package app.shamilton.timecard.command

import app.shamilton.timecard.App
import app.shamilton.timecard.Color.yellow
import app.shamilton.timecard.Color.magenta
import app.shamilton.timecard.Color.green
import app.shamilton.timecard.config.CleanInterval
import app.shamilton.timecard.config.Configuration

class ConfigCommand : ICommand {

	override val m_Name: String = "CONFIG"
	override val m_Help: String = "" // TODO
	override val m_DetailedHelp: String? = null //TODO: List configurations
	override val m_HelpArgs: List<String> = listOf() // TODO

	override fun execute() {
		val configName = App.getArg(1)
		val configValue = App.getArg(2)

		if(configName == null){
			println(yellow("Invalid number of arguments. See 'timecard help config' for usage."))
			return
		}

		val config = Configuration.loadFromFile()

		// TODO: Hmm... I should make configuration classes
		when(configName) {
			"CLEAN_INTERVAL" -> {
				if(configValue == null){
					// display value
					println("clean_interval is currently set to ${magenta(config.clean_interval.toString())}")
				} else {
					// set value
					val cleanInterval: CleanInterval? = CleanInterval.get(configValue.trim())
					if (cleanInterval == null) {
						println(yellow("Unknown value for clean_interval. Known values are ${magenta("manually")}${yellow(",")} ${magenta("daily")}${yellow(",")} ${magenta("weekly")}${yellow(",")} and ${magenta("monthly")}${yellow(".")}"))
						return
					}
					config.clean_interval = cleanInterval
					println("Set ${green(configName.lowercase())} to ${green(configValue)}")
				}
			}
			else -> {
				println(yellow("Unknown configuration! See 'timecard help config' for list of configs."))
				return
			}
		}

		config.saveToFile()
	}

}