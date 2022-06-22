package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.App
import app.shamilton.timecardkt.Color.yellow
import app.shamilton.timecardkt.Color.cyan
import app.shamilton.timecardkt.config.Configuration
import app.shamilton.timecardkt.entry.TimeEntries
import java.awt.Desktop
import java.io.File
import java.nio.file.Files

class OpenCommand : ICommand {

	override val m_Name: String = "OPEN"
	override val m_Help: String = "Opens the directory where ${App.NAME} config or data files are stored, depending on the argument supplied."
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
				println(yellow("Unknown argument. See '${App.NAME} help open' for usage"))
				return
			}
		}

		if(!directory.exists()){
			Files.createDirectories(directory.toPath())
		}

		try {
			val desktop = Desktop.getDesktop()
			desktop.open(directory)
		} catch(e: Exception){
			// If for some reason we can't open the file manager GUI, just print out the location.
			// In this case, we can just tell the user where the files are located.
			println("${App.NAME} ${arg.lowercase()} files are located at ${cyan(directory.path)}")
		}
	}

}