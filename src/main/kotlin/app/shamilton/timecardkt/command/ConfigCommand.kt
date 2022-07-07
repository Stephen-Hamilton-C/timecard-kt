package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.App
import app.shamilton.timecardkt.Color.yellow
import app.shamilton.timecardkt.Color.MAGENTA
import app.shamilton.timecardkt.Color.RESET
import app.shamilton.timecardkt.Color.YELLOW
import app.shamilton.timecardkt.Color.magenta
import app.shamilton.timecardkt.config.Config
import app.shamilton.timecardkt.config.ConfigList

class ConfigCommand : ICommand {

	override val m_Name: String = "CONFIG"
	override val m_Help: String = "Displays or sets user preferences."
	override val m_DetailedHelp: String
		get() {
			var detailedHelp = ""
			for ((i: Int, config: Config) in ConfigList.CONFIGS.withIndex()) {
				val tab = if(i == 0) "" else "	"
				detailedHelp += "$tab- ${config.m_Name}: ${config.m_Help}\n"
				detailedHelp += "		Possible values: ${getColorfulValues(config)}\n"
			}
			return detailedHelp
		}
	override val m_HelpArgs: List<String> = listOf("<name>", "[value]")

	private fun getColorfulValues(config: Config, originalColor: String = RESET): String = "$MAGENTA${config.m_PossibleValues.joinToString("$originalColor, $MAGENTA")}$originalColor.$RESET"

	override fun execute() {
		val configName = App.getArg(1)
		val newConfigValue = App.getArg(2)

		if(configName == null){
			println("Full list of configurations:")
			println("	$m_DetailedHelp")
			return
		}

		val foundConfig: Config? = ConfigList.CONFIGS.find { it.m_Name == configName }
		if(foundConfig == null) {
			println(yellow("Unknown configuration! Here's the full list of configurations:"))
			println("	$m_DetailedHelp")
			return
		}

		if(newConfigValue == null){
			// Display value
			println("${foundConfig.m_Name.lowercase()} is currently set to ${magenta(foundConfig.m_Value)}")
			println(foundConfig.m_Help)
			println("Possible values: ${getColorfulValues(foundConfig)}")
		} else if(foundConfig.m_PossibleValues.contains(newConfigValue)) {
			// Set value
			foundConfig.m_Value = newConfigValue
			println("Set ${foundConfig.m_Name.lowercase()} to ${magenta(newConfigValue)}")
		} else {
			// Invalid value
			println(yellow("Unknown value for ${foundConfig.m_Name.lowercase()}. Known values are ${getColorfulValues(foundConfig, YELLOW)}"))
		}
	}

}