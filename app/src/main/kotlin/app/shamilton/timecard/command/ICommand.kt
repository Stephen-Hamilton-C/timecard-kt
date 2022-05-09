package app.shamilton.timecard.command

interface ICommand {

	val m_Name: String;
	val m_Help: String;
	
	fun execute();

}
