package app.shamilton.timecard.command

import app.shamilton.timecard.core.TimeEntries
import app.shamilton.timecard.core.TimeEntry
import com.github.ajalt.mordant.rendering.TextColors.red
import com.github.ajalt.mordant.terminal.Terminal
import java.time.LocalTime

class ClockInCommand : ICommand {
	
	override val m_Name: String = "IN"

	private val _t = Terminal()

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if (timeEntries.isClockedIn()) {
			_t.println(red("Already clocked in!"))
			println("Use 'timecard out' to clock out.")
			return
		}

		val newEntry = TimeEntry(LocalTime.now())
		timeEntries.m_Entries.add(newEntry)

		timeEntries.saveToFile()
	}

}
