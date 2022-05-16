package app.shamilton.timecard.command

import app.shamilton.timecard.App
import app.shamilton.timecard.entry.TimeEntries
import app.shamilton.timecard.entry.TimeEntry
import app.shamilton.timecard.serializer.LocalTimeSerializer
import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

class ClockInCommand : ICommand {
	
	override val m_Name: String = "IN"
	override val m_Help: String = "" // TODO

	private val _t = Terminal()

	private fun now(): LocalTime {
		return LocalTime.now().truncatedTo(ChronoUnit.MINUTES)
	}

	private fun getTimeFromOffset(timeEntries: TimeEntries, offset: String): LocalTime? {
		// Assume minutes offset
		try {
			val offsetMinutes: Long = offset.toLong().absoluteValue

			// Cannot clock in yesterday
			if (now().toSecondOfDay() - offsetMinutes * 60 < 0){
				_t.println(yellow("You cannot clock in before midnight! Did you forget a colon?"))
				return null
			}

			return now().minusMinutes(offsetMinutes)
		} catch(e: NumberFormatException) {
			_t.println(yellow("Unknown arguments. Use 'timecard help in' for usage."))
			return null
		}
	}

	private fun getTime(offset: String): LocalTime? {
		try {
			return LocalTimeSerializer.decodeData(offset)
		} catch (e: IllegalStateException) {
			// User input error
			_t.println(yellow("Unknown arguments. Use 'timecard help in' for usage."))
			return null;
		}
	}

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if (timeEntries.isClockedIn()) {
			_t.println(yellow("Already clocked in!"))
			println("Use 'timecard out' to clock out.")
			return
		}

		val arg: String? = App.getArg(1)
		val startTime: LocalTime = if(arg == null) {
			now()
		} else if(arg.contains(":")) {
			getTime(arg) ?: return
		} else {
			getTimeFromOffset(timeEntries, arg) ?: return
		}

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
