package app.shamilton.timecard.command;

import app.shamilton.timecard.core.TimeEntries;
import app.shamilton.timecard.core.TimeEntry;
import java.time.LocalTime

class ClockInCommand : ICommand {
	
	override val m_Name: String = "IN";
	override val m_Help: String = "";

	private val _timeEntries = TimeEntries.loadFromFile();

	override fun execute() {
		if (_timeEntries.isClockedIn()) {
			println("Already clocked in! Use 'timecard out' to clock out.");
			return
		};

		val newEntry = TimeEntry(LocalTime.now());
		_timeEntries.m_Entries.add(newEntry);

		_timeEntries.saveToFile();
	}

}
