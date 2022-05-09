package app.shamilton.timecard.command;

import app.shamilton.timecard.core.TimeEntries;
import app.shamilton.timecard.core.TimeEntry;
import java.time.LocalTime

class ClockOutCommand : ICommand {
	
	override val m_Name: String = "OUT";
	override val m_Help: String = "";

	private val _timeEntries = TimeEntries.loadFromFile();

	override fun execute() {
		if (_timeEntries.isClockedOut()) {
			println("Already clocked out! Use 'timecard in' to clock in.");
			return
		}

		val lastEntry: TimeEntry = _timeEntries.m_Entries.last();
		lastEntry.m_EndTime = LocalTime.now();

		_timeEntries.saveToFile();
	}

}
