package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.Color.yellow
import app.shamilton.timecardkt.Color.green
import app.shamilton.timecardkt.Color.red
import app.shamilton.timecardkt.entry.TimeEntries
import app.shamilton.timecardkt.entry.TimeEntry

class LogCommand : ICommand {

	override val m_Name: String = "LOG"
	override val m_Help: String = "Shows history of clocking in and out for today"
	override val m_DetailedHelp: String? = null
	override val m_HelpArgs: List<String> = listOf()

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if(timeEntries.m_Entries.isEmpty()){
			println(yellow("You haven't clocked in yet today!"))
			return
		}

		for (entry: TimeEntry in timeEntries.m_Entries) {
			println(green("Clocked in: ${entry.startTime}"))
			if(entry.endTime != null){
				println(red("Clocked out: ${entry.endTime}"))
			}
		}
	}

}