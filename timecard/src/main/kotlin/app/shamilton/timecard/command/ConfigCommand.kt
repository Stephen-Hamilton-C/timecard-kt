package app.shamilton.timecard.command

import app.shamilton.timecard.App
import app.shamilton.timecard.Color.yellow
import app.shamilton.timecard.Color.MAGENTA
import app.shamilton.timecard.Color.YELLOW
import app.shamilton.timecard.Color.magenta
import app.shamilton.timecard.config.Config
import app.shamilton.timecard.config.ConfigList

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

		val foundConfig: Config? = ConfigList.CONFIGS.find { it.m_Name == configName }
		if(foundConfig == null) {
			println(yellow("Unknown configuration! See 'timecard help config' for list of configs."))
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