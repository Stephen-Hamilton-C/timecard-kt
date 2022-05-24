package app.shamilton.timecard.command

import app.shamilton.timecard.App
import app.shamilton.timecard.config.CleanInterval
import app.shamilton.timecard.config.Configuration
import com.github.ajalt.mordant.rendering.TextColors.yellow
import com.github.ajalt.mordant.rendering.TextColors.brightMagenta
import com.github.ajalt.mordant.rendering.TextColors.green

import com.github.ajalt.mordant.terminal.Terminal

class ConfigCommand : ICommand {

	override val m_Name: String = "CONFIG"
	override val m_Help: String = "" // TODO
	override val m_DetailedHelp: String? = null //TODO: List configurations
	override val m_HelpArgs: List<String> = listOf() // TODO

	private val _t = Terminal()

	override fun execute() {
		val configName = App.getArg(1)
		val configValue = App.getArg(2)

		if(configName == null){
			_t.println(yellow("Invalid number of arguments. See 'timecard help config' for usage."))
			return
		}

		val config = Configuration.loadFromFile()

		// TODO: Hmm... I should make configuration classes
		when(configName) {
			"CLEAN_INTERVAL" -> {
				if(configValue == null){
					// display value
					_t.println("clean_interval is currently set to ${brightMagenta(config.clean_interval.toString())}")
				} else {
					// set value
					val cleanInterval: CleanInterval? = CleanInterval.get(configValue.trim())
					if (cleanInterval == null) {
						_t.println(yellow("Unknown value for clean_interval. Known values are ${brightMagenta("manually")}${yellow(",")} ${brightMagenta("daily")}${yellow(",")} ${brightMagenta("weekly")}${yellow(",")} and ${brightMagenta("monthly")}${yellow(".")}"))
						return
					}
					config.clean_interval = cleanInterval
					_t.println("Set ${green(configName.lowercase())} to ${green(configValue)}")
				}
			}
			else -> {
				_t.println(yellow("Unknown configuration! See 'timecard help config' for list of configs."))
				return
			}
		}

		config.saveToFile()
	}

}