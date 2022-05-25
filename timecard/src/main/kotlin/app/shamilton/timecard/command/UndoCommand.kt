package app.shamilton.timecard.command

import app.shamilton.timecard.Color.yellow
import app.shamilton.timecard.Color.green
import app.shamilton.timecard.Color.red
import app.shamilton.timecard.entry.TimeEntries
import app.shamilton.timecard.entry.TimeEntry

class UndoCommand : ICommand {

	override val m_Name: String = "UNDO"
	override val m_Help: String = "Undoes the last clock in/out action"
	override val m_DetailedHelp: String? = null
	override val m_HelpArgs: List<String> = listOf()

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile()
		if (timeEntries.m_Entries.isEmpty()){
			println(yellow("Nothing to undo!"))
			println("Clock in first using 'timecard in'.")
			return
		}

		if(timeEntries.isClockedIn()){
			val removedEntry: TimeEntry = timeEntries.m_Entries.removeLast()
			println("Undo: clock ${green("in")} at ${green(removedEntry.startTime.toString())}")

			if(timeEntries.m_Entries.isEmpty()) {
				timeEntries.deleteFile()
				println(red("Clocked out. No time log remains for today."))
			} else {
				timeEntries.saveToFile()
				val lastEntry: TimeEntry = timeEntries.m_Entries.last()
				println(red("Clocked out since ${lastEntry.endTime}"))
			}
		} else {
			val lastEntry: TimeEntry = timeEntries.m_Entries.last()
			println("Undo: clock ${red("out")} at ${red(lastEntry.endTime.toString())}")
			println(green("Clocked in since ${lastEntry.startTime}"))

			lastEntry.endTime = null
			timeEntries.saveToFile()
		}
	}

}