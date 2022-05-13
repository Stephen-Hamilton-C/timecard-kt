package app.shamilton.timecard.command

import app.shamilton.timecard.entry.TimeEntries
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import java.awt.Desktop
import java.awt.HeadlessException
import java.io.File

class OpenCommand : ICommand {

	override val m_Name: String = "OPEN"
	override val m_Help: String = "" // TODO

	private val _t = Terminal()

	override fun execute() {
		try {
			val desktop = Desktop.getDesktop()
			desktop.open(File(TimeEntries.FILEPATH))
		} catch(e: HeadlessException){
			// If the user doesn't have a GUI, a HeadlessException will be thrown.
			// In this case, we can just tell the user where the files are located.
			_t.println("Timecard files are located at ${cyan(TimeEntries.FILEPATH)}")
		}
	}

}