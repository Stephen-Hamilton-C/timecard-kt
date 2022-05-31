package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.App
import app.shamilton.timecardkt.Color.yellow
import app.shamilton.timecardkt.Color.green
import app.shamilton.timecardkt.entry.TimeEntries
import app.shamilton.timecardkt.entry.TimeEntry
import java.time.LocalTime

class ClockInCommand : ClockCommand() {
	
	override val m_Name: String = "IN"
	override val m_Help: String = "Clocks you in. If an offset is supplied, it will clock you in OFFSET minutes ago or at OFFSET time. Time must be in 24-hour time (e.g. 17:31)"

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if (timeEntries.isClockedIn()) {
			println(yellow("Already clocked in!"))
			println("Use '${App.NAME} out' to clock out.")
			return
		}

		val startTime: LocalTime = getTime() ?: return

		// Cannot clock in to the future
		if (startTime.isAfter(now())) {
			println(yellow("Cannot clock in into the future!"))
			return
		}

		if (timeEntries.m_Entries.isNotEmpty()) {
			// Already checked if currently clocked out
			// If m_Entries has an entry, and we are clocked out, endTime must exist
			val lastClockOut: LocalTime = timeEntries.m_Entries.last().endTime!!

			// Cannot clock in before last clock out
			if (startTime.isBefore(lastClockOut)) {
				println(yellow("Clock in time cannot be before last clock out time!"))
				println("Use '${App.NAME} undo' to undo last clock out.")
				return
			}
		}

		val newEntry = TimeEntry(startTime)
		timeEntries.m_Entries.add(newEntry)

		println("Clocked ${green("in")} at ${green(newEntry.formattedStartTime)}")

		timeEntries.saveToFile()
	}

}
