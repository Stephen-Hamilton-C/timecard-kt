package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.App
import app.shamilton.timecardkt.Color.GREEN
import app.shamilton.timecardkt.Color.RED
import app.shamilton.timecardkt.Color.colorize
import app.shamilton.timecardkt.Color.yellow
import app.shamilton.timecardkt.config.Configuration
import app.shamilton.timecardkt.config.timeformat.TimeFormat
import app.shamilton.timecardkt.entry.TimeEntries
import app.shamilton.timecardkt.entry.TimeEntry
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

class StatusCommand : ICommand {

	override val m_Name: String = "STATUS"
	override val m_Help: String = "Displays whether you're currently clocked in or not and how long you've been working today."
	override val m_DetailedHelp: String? = null
	override val m_HelpArgs: List<String> = listOf()

	private fun calculateDifference(startTime: LocalTime, endTime: LocalTime): Long {
		val startTimeMinutes: Int = startTime.hour * 60 + startTime.minute
		val difference: LocalTime = endTime.minusMinutes(startTimeMinutes.toLong())
		val differenceMinutes: Int = difference.hour * 60 + difference.minute
		return differenceMinutes.toLong()
	}

	private fun getTimeWorked(timeEntries: TimeEntries): LocalTime {
		var timeWorked = LocalTime.of(0, 0)
		for (entry: TimeEntry in timeEntries.m_Entries) {
			val startTime: LocalTime = entry.startTime
			val endTime: LocalTime = entry.endTime ?: LocalTime.now().truncatedTo(ChronoUnit.MINUTES)

			timeWorked = timeWorked.plusMinutes(calculateDifference(startTime, endTime))
		}

		return timeWorked
	}

	private fun getTimeOnBreak(timeEntries: TimeEntries): LocalTime {
		var timeOnBreak = LocalTime.of(0, 0)
		val entries: List<TimeEntry> = timeEntries.m_Entries
		for ((i, entry: TimeEntry) in entries.withIndex()) {
			if (i == 0) continue
			// Last entry would always have an endTime
			val endTime: LocalTime = entries[i - 1].endTime!!
			val startTime: LocalTime = entry.startTime

			timeOnBreak = timeOnBreak.plusMinutes(calculateDifference(endTime, startTime))
		}
		// Add break time if currently clocked out
		if(timeEntries.isClockedOut()) {
			// TimeEntries won't be empty since that check should be done before any of this executes
			// This means the last entry will certainly have an endTime
			val lastEntry: TimeEntry = timeEntries.m_Entries.last()
			val endTime: LocalTime = lastEntry.endTime!!
			val now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES)

			timeOnBreak = timeOnBreak.plusMinutes(calculateDifference(endTime, now))
		}

		return timeOnBreak
	}

	private fun formatTime(time: LocalTime): String {
		val config = Configuration.loadFromFile()
		when(config.time_format) {
			TimeFormat.ISO -> return "${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}"
			TimeFormat.QUARTER_HOUR -> {
				val quarterHour: Double = time.hour + ((time.minute / 15.0).roundToInt() * 15.0) / 60.0
				return "$quarterHour hours"
			}
			TimeFormat.WRITTEN -> {
				val hours: String = if(time.hour == 1) "hour" else "hours"
				val minutes: String = if(time.minute == 1) "minute" else "minutes"
				return if(time.hour > 0)
					"${time.hour} $hours and ${time.minute} $minutes"
				else
					"${time.minute} $minutes"
			}
			TimeFormat.WRITTEN_SHORT -> {
				val hours: String = if(time.hour == 1) "hr" else "hrs"
				val minutes: String = if(time.minute == 1) "min" else "mins"
				return if(time.hour > 0)
					"${time.hour} $hours and ${time.minute} $minutes"
				else
					"${time.minute} $minutes"
			}
		}
	}

	private fun printClockState(timeEntries: TimeEntries) {
		val lastEntry: TimeEntry = timeEntries.m_Entries.last()
		val lastTime: String = lastEntry.formattedEndTime ?: lastEntry.formattedStartTime

		val clockState = if(timeEntries.isClockedIn()) "in" else "out"
		val color = if(timeEntries.isClockedIn()) GREEN else RED
		println(colorize(color, "Currently clocked $clockState since $lastTime"))
	}

	private fun printTimeWorked(timeEntries: TimeEntries) {
		val timeWorked: LocalTime = getTimeWorked(timeEntries)
		println("Worked for ${formatTime(timeWorked)}")
	}

	private fun printBreakTime(timeEntries: TimeEntries) {
		val timeOnBreak: LocalTime = getTimeOnBreak(timeEntries)
		println("On break for ${formatTime(timeOnBreak)}")
	}

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if(timeEntries.m_Entries.isEmpty()) {
			println(yellow("You haven't clocked in yet today! Use '${App.NAME} in' to clock in."))
			return
		}

		printClockState(timeEntries)
		printTimeWorked(timeEntries)
		printBreakTime(timeEntries)
	}

}