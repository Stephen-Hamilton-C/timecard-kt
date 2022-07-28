package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.App
import app.shamilton.timecardkt.Color.yellow
import app.shamilton.timecardkt.Color.green
import app.shamilton.timecardkt.Color.red
import app.shamilton.timecardkt.entry.TimeEntries
import app.shamilton.timecardkt.entry.TimeEntry
import java.time.LocalDate
import kotlin.math.abs

class LogCommand : ICommand {

	override val m_Name: String = "LOG"
	override val m_Help: String = "Shows history of clocking in and out for today. If days argument is supplied, shows log for a timecard that many days ago."
	override val m_DetailedHelp: String? = null
	override val m_HelpArgs: List<String> = listOf("[days]")

	private fun printLog(timeEntries: TimeEntries) {
		for (entry: TimeEntry in timeEntries.m_Entries) {
			println(green("Clocked in: ${entry.formattedStartTime}"))
			if(entry.endTime != null){
				println(red("Clocked out: ${entry.formattedEndTime}"))
			}
		}
	}

	override fun execute() {
		val daysPrior: Long = abs(try {
			App.getArg(1)?.toLong() ?: 0L
		} catch (e: NumberFormatException) {
			0L
		})

		if (daysPrior == 0L) {
			val timeEntries = TimeEntries.loadFromFile()
			if (timeEntries.m_Entries.isEmpty()) {
				println(yellow("You haven't clocked in yet today!"))
				return
			}
			printLog(timeEntries)
		} else {
			val timeEntries = TimeEntries.loadFromFile(daysPrior)
			if(timeEntries == null || timeEntries.m_Entries.isEmpty()) {
				val s = if (daysPrior == 1L) { "" } else { "s" }
				println(yellow("No timecard present for $daysPrior day$s ago."))
				return
			}
			println("Timecard log for ${LocalDate.now().minusDays(daysPrior)}:")
			printLog(timeEntries)
		}
	}
}