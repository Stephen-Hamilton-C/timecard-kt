package app.shamilton.timecard.command

import app.shamilton.timecard.core.TimeEntries
import app.shamilton.timecard.core.TimeEntry
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class ClockOutCommand : ICommand {
	
	override val m_Name: String = "OUT"

	private val _t = Terminal()

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if (timeEntries.isClockedOut()) {
			_t.println(yellow("Already clocked out!"))
			println("Use 'timecard in' to clock in.")
			return
		}

		// TODO: Use App.args[1] to determine offset

		val now = LocalTime.now()
		val lastEntry: TimeEntry = timeEntries.m_Entries.last()
		lastEntry.endTime = now.truncatedTo(ChronoUnit.MINUTES)

		_t.println("Clocked ${red("out")} at ${red(lastEntry.endTime.toString())}")

		timeEntries.saveToFile()
	}

}
