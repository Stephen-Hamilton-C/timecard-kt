package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.App

class HelpCommand : ICommand {

	override val m_Name: String = "HELP"
	override val m_Help: String = "Shows this help message. If a command is given, shows detailed help for that command."
	override val m_DetailedHelp: String? = null
	override val m_HelpArgs: List<String> = listOf("[command]")

	private fun printHelp(command: ICommand, detailed: Boolean) {
		val argSpace: String = if (command.m_HelpArgs.isEmpty()) "" else " "
		val commandUsage = "${command.m_Name.lowercase()}$argSpace${command.m_HelpArgs.joinToString(" ")}"
		if(detailed){
			println("$commandUsage:")
			println("	${command.m_DetailedHelp ?: command.m_Help}")
		} else {
			println("	$commandUsage - ${command.m_Help}")
		}
	}

	override fun execute() {
		println("Usage: ${App.NAME} <command>")
		val specificCommand: ICommand? = CommandList.COMMANDS.find { it.m_Name == App.getArg(1) }
		if(specificCommand != null){
			// Print help for a specific command
			printHelp(specificCommand, true)
		} else {
			// Print helps for all commands
			for (command: ICommand in CommandList.COMMANDS) {
				printHelp(command, false)
			}
		}
	}

}
