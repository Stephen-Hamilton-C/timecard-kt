package app.shamilton.adhdtimecard.command

abstract class Command {

	abstract val m_name: String;
	abstract val m_help: String;
	abstract val m_alias: String?;
	
	abstract fun execute();

}
