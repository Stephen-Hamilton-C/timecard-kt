package app.shamilton.adhdtimecard.command;

import app.shamilton.adhdtimecard.core.TimeEntries;
import app.shamilton.adhdtimecard.core.TimeEntry;
import java.time.LocalTime

class ClockOutCommand(val timeEntries: TimeEntries) : Command() {
	
	public override val m_name: String = "out";
	public override val m_help: String = "";
	public override val m_alias: String? = "o";

	public override fun execute() {
		if (timeEntries.isClockedOut()) return;

		val lastEntry: TimeEntry = timeEntries.m_entries.last();
		lastEntry.m_endTime = LocalTime.now();
	}

}
