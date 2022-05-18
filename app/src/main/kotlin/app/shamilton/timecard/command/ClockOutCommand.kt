package app.shamilton.timecard.command

import app.shamilton.timecard.entry.TimeEntries
import app.shamilton.timecard.entry.TimeEntry
import com.github.ajalt.mordant.rendering.TextColors.*
import java.time.LocalTime

class ClockOutCommand : ClockCommand() {
	
	override val m_Name: String = "OUT"
	override val m_Help: String = "" // TODO

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if (timeEntries.isClockedOut()) {
			_t.println(yellow("Already clocked out!"))
			println("Use 'timecard in' to clock in.")
			return
		}

		val endTime: LocalTime = getTime() ?: return

		// Cannot clock out in to the future
		if (endTime.isAfter(now())) {
			_t.println(yellow("Cannot clock out into the future!"))
			return
		}

		val lastClockIn: LocalTime = timeEntries.m_Entries.last().startTime
		if (endTime.isBefore(lastClockIn)) {
			_t.println(yellow("Clock out time cannot be before last clock in time!"))
			println("Use 'timecard undo' to undo last clock in.")
			return
		}

		val lastEntry: TimeEntry = timeEntries.m_Entries.last()
		lastEntry.endTime = endTime

		_t.println("Clocked ${red("out")} at ${red(lastEntry.endTime.toString())}")

		timeEntries.saveToFile()
	}

}
