package app.shamilton.timecard.command

import app.shamilton.timecard.App
import app.shamilton.timecard.Color.yellow
import app.shamilton.timecard.Color.cyan
import app.shamilton.timecard.config.Configuration
import app.shamilton.timecard.entry.TimeEntries
import java.awt.Desktop
import java.awt.HeadlessException
import java.io.File
import java.nio.file.Files

class OpenCommand : ICommand {

	override val m_Name: String = "OPEN"
	override val m_Help: String = "Opens the directory where timecard config or data files are stored, depending on the argument supplied."
	override val m_DetailedHelp: String? = null
	override val m_HelpArgs: List<String> = listOf("<config/data>")

	override fun execute() {
		val arg: String? = App.getArg(1)
		val directory: File = when(arg) {
			"CONFIG" -> Configuration.FILEPATH.toFile()
			"DATA" -> TimeEntries.FILEPATH.toFile()
			null -> {
				println(yellow("Must specify which directory! 'config' or 'data'?"))
				return
			}
			else -> {
				println(yellow("Unknown argument. See 'timecard help open' for usage"))
				return
			}
		}

		if(!directory.exists()){
			Files.createDirectories(directory.toPath())
		}

		try {
			val desktop = Desktop.getDesktop()
			desktop.open(directory)
		} catch(e: HeadlessException){
			// If the user doesn't have a GUI, a HeadlessException will be thrown.
			// In this case, we can just tell the user where the files are located.
			println("timecard ${arg.lowercase()} files are located at ${cyan(directory.path)}")
		}
	}

}