package app.shamilton.adhdtimecard.command;

import app.shamilton.adhdtimecard.core.TimeEntries

class CommandRetriever(val timeEntries: TimeEntries, val args: List<String>) {

	private val _commands: List<Command> = listOf(
		HelpCommand(args[0]),
		ClockInCommand(timeEntries, args[0]),
		ClockOutCommand(timeEntries, args[0])
	);

	public fun get(name: String): Command {
		val lowerName = name.lowercase();
		for(command in _commands) {
			if(command.m_name == lowerName || command.m_alias == lowerName){
				return command;
			}
		}
	
		return HelpCommand();
	}

}
