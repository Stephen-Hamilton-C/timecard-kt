package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.App
import app.shamilton.timecardkt.Color.yellow
import app.shamilton.timecardkt.Color.green
import app.shamilton.timecardkt.Color.red
import app.shamilton.timecardkt.entry.TimeEntries
import app.shamilton.timecardkt.entry.TimeEntry

class UndoCommand : ICommand {

	override val m_Name: String = "UNDO"
	override val m_Help: String = "Undoes the last clock in/out action."
	override val m_DetailedHelp: String? = null
	override val m_HelpArgs: List<String> = listOf()

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if (timeEntries.m_Entries.isEmpty()){
			println(yellow("Nothing to undo!"))
			println("Clock in first using '${App.NAME} in'.")
			return
		}

		if(timeEntries.isClockedIn()){
			val removedEntry: TimeEntry = timeEntries.m_Entries.removeLast()
			println("Undo: clock ${green("in")} at ${green(removedEntry.formattedStartTime)}")

			if(timeEntries.m_Entries.isEmpty()) {
				timeEntries.deleteFile()
				println(red("Clocked out. No time log remains for today."))
			} else {
				timeEntries.saveToFile()
				val lastEntry: TimeEntry = timeEntries.m_Entries.last()
				println(red("Clocked out since ${lastEntry.formattedEndTime}"))
			}
		} else {
			val lastEntry: TimeEntry = timeEntries.m_Entries.last()
			println("Undo: clock ${red("out")} at ${red(lastEntry.formattedEndTime)}")
			println(green("Clocked in since ${lastEntry.formattedStartTime}"))

			lastEntry.endTime = null
			timeEntries.saveToFile()
		}
	}

}