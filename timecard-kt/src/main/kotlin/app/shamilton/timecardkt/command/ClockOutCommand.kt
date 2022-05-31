package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.App
import app.shamilton.timecardkt.Color.yellow
import app.shamilton.timecardkt.Color.red
import app.shamilton.timecardkt.entry.TimeEntries
import app.shamilton.timecardkt.entry.TimeEntry
import java.time.LocalTime

class ClockOutCommand : ClockCommand() {
	
	override val m_Name: String = "OUT"
	override val m_Help: String = "Clocks you out. If an offset is supplied, it will clock you out OFFSET minutes ago or at OFFSET time. Time must be in 24-hour time (e.g. 17:31)"

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if (timeEntries.isClockedOut()) {
			println(yellow("Already clocked out!"))
			println("Use '${App.NAME} in' to clock in.")
			return
		}

		val endTime: LocalTime = getTime() ?: return

		// Cannot clock out in to the future
		if (endTime.isAfter(now())) {
			println(yellow("Cannot clock out into the future!"))
			return
		}

		val lastClockIn: LocalTime = timeEntries.m_Entries.last().startTime
		if (endTime.isBefore(lastClockIn)) {
			println(yellow("Clock out time cannot be before last clock in time!"))
			println("Use '${App.NAME} undo' to undo last clock in.")
			return
		}

		val lastEntry: TimeEntry = timeEntries.m_Entries.last()
		lastEntry.endTime = endTime

		println("Clocked ${red("out")} at ${red(lastEntry.endTime.toString())}")

		timeEntries.saveToFile()
	}

}
