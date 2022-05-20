package app.shamilton.timecard.command

object CommandList {
	val COMMANDS: List<ICommand> = listOf(
		StatusCommand(),
		HelpCommand(),
		VersionCommand(),
		ClockInCommand(),
		ClockOutCommand(),
		UndoCommand(),
		LogCommand(),
		OpenCommand(),
		ConfigCommand(),
		CleanCommand(),
	)
}