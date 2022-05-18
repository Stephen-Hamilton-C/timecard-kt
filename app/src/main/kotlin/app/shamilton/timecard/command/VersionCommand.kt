package app.shamilton.timecard.command

import app.shamilton.timecard.App

class VersionCommand : ICommand {

	override val m_Name: String = "VERSION"
	override val m_Help: String = "Displays the current version of timecard"
	override val m_HelpArgs: List<String> = listOf()

	override fun execute() {
		println("timecard version ${App.VERSION}")
	}

}