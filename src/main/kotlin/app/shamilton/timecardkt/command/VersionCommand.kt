package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.App

class VersionCommand : ICommand {

	override val m_Name: String = "VERSION"
	override val m_Help: String = "Displays the current version of ${App.NAME}"
	override val m_DetailedHelp: String? = null
	override val m_HelpArgs: List<String> = listOf()

	override fun execute() {
		println("${App.NAME} version ${App.VERSION}")
	}

}