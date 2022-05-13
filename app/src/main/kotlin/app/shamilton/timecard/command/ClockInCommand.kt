package app.shamilton.timecard.command

import app.shamilton.timecard.entry.TimeEntries
import app.shamilton.timecard.entry.TimeEntry
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class ClockInCommand : ICommand {
	
	override val m_Name: String = "IN"
	override val m_Help: String = "" // TODO

	private val _t = Terminal()

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if (timeEntries.isClockedIn()) {
			_t.println(yellow("Already clocked in!"))
			println("Use 'timecard out' to clock out.")
			return
		}

		// TODO: Use App.args[1] to determine offset

		val now = LocalTime.now()
		val newEntry = TimeEntry(now.truncatedTo(ChronoUnit.MINUTES))
		timeEntries.m_Entries.add(newEntry)

		_t.println("Clocked ${green("in")} at ${green(newEntry.startTime.toString())}")

		timeEntries.saveToFile()
	}

}
