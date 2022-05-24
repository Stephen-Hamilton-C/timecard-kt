package app.shamilton.timecard.command

interface ICommand {

	val m_Name: String
	val m_Help: String
	val m_DetailedHelp: String?
	val m_HelpArgs: List<String>

	fun execute()

}
