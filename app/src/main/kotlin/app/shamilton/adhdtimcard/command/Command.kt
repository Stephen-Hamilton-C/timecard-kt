package app.shamilton.adhdtimcard.command

abstract class Command() {

	abstract val m_Name: String;
	abstract val m_Help: String;
	
	abstract fun execute();

}
