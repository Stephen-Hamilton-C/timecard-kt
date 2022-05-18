package app.shamilton.timecard.command

import app.shamilton.timecard.App

class HelpCommand : ICommand {

	override val m_Name: String = "HELP"
	override val m_Help: String = "Shows this help message. If a command is given, shows only help for that command"
	override val m_HelpArgs: List<String> = listOf("[command]")

	private fun printHelp(command: ICommand) {
		val argSpace = if (command.m_HelpArgs.isEmpty()) "" else " "
		println("	${command.m_Name.lowercase()} ${command.m_HelpArgs.joinToString(" ")}$argSpace- ${command.m_Help}")
	}

	override fun execute() {
		println("Usage: timecard <command>")
		val specificCommand: ICommand? = CommandList.COMMANDS.find { it.m_Name == App.getArg(1) }
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
