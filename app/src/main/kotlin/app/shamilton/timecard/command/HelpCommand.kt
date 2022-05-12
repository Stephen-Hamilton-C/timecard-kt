package app.shamilton.timecard.command

import app.shamilton.timecard.App

class HelpCommand : ICommand {

	override val m_Name: String = "HELP"
	override val m_Help: String = "" // TODO

	private fun printHelp(command: ICommand) {
		println("${command.m_Name.lowercase()} - ${command.m_Help}")
	}

	override fun execute() {
		val app = App()
		val specificCommand: ICommand? = CommandList.COMMANDS.find { it.m_Name == app.getArg(1)?.uppercase() }
		if(specificCommand != null){
			// Print help for a specific command
			printHelp(specificCommand)
		} else {
			// Print helps for all commands
			for (command: ICommand in CommandList.COMMANDS) {
				printHelp(command)
			}
		}
	}

}
