package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.App
import app.shamilton.timecardkt.Color.yellow
import app.shamilton.timecardkt.Color.MAGENTA
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
				detailedHelp += "		Possible values: ${config.m_PossibleValues.joinToString(", ")}\n"
			}
			return detailedHelp
		}
	override val m_HelpArgs: List<String> = listOf("<name>", "[value]")

	override fun execute() {
		val configName = App.getArg(1)
		val configValue = App.getArg(2)

		if(configName == null){
			println(yellow("Invalid number of arguments. See '${App.NAME} help config' for usage."))
			return
		}

		val foundConfig: Config? = ConfigList.CONFIGS.find { it.m_Name == configName }
		if(foundConfig == null) {
			println(yellow("Unknown configuration! See '${App.NAME} help config' for list of configs."))
			return
		}

		if(configValue == null){
			// Display value
			println("${foundConfig.m_Name.lowercase()} is currently set to ${magenta(foundConfig.m_Value)}")
		} else if(foundConfig.m_PossibleValues.contains(configValue)) {
			// Set value
			foundConfig.m_Value = configValue
			println("Set ${foundConfig.m_Name.lowercase()} to ${magenta(configValue)}")
		} else {
			// Invalid value
			println(yellow("Unknown value for ${foundConfig.m_Name.lowercase()}. Known values are $MAGENTA${foundConfig.m_PossibleValues.joinToString("$YELLOW, $MAGENTA")}${yellow(".")}"))
		}
	}

}