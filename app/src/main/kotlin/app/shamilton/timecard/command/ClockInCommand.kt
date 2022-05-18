package app.shamilton.timecard.command

import app.shamilton.timecard.entry.TimeEntries
import app.shamilton.timecard.entry.TimeEntry
import com.github.ajalt.mordant.rendering.TextColors.*
import java.time.LocalTime

class ClockInCommand : ClockCommand() {
	
	override val m_Name: String = "IN"
	override val m_Help: String = "" // TODO

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if (timeEntries.isClockedIn()) {
			_t.println(yellow("Already clocked in!"))
			println("Use 'timecard out' to clock out.")
			return
		}

		val startTime: LocalTime = getTime() ?: return

		// Cannot clock in to the future
		if (startTime.isAfter(now())) {
			_t.println(yellow("Cannot clock in into the future!"))
			return
		}

		if (timeEntries.m_Entries.isNotEmpty()) {
			// Already checked if currently clocked out
			// If m_Entries has an entry, and we are clocked out, endTime must exist
			val lastClockOut: LocalTime = timeEntries.m_Entries.last().endTime!!

			// Cannot clock in before last clock out
			if (startTime.isBefore(lastClockOut)) {
				_t.println(yellow("Clock in time cannot be before last clock out time!"))
				println("Use 'timecard undo' to undo last clock out.")
				return
			}
		}

		val newEntry = TimeEntry(startTime)
		timeEntries.m_Entries.add(newEntry)

		_t.println("Clocked ${green("in")} at ${green(newEntry.startTime.toString())}")

		timeEntries.saveToFile()
	}

}
