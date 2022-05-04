package app.shamilton.adhdtimecard.command;

import app.shamilton.adhdtimecard.core.TimeEntries;
import app.shamilton.adhdtimecard.core.TimeEntry;
import java.time.LocalTime

class ClockInCommand(val timeEntries: TimeEntries, private var _time: LocalTime = LocalTime.now()) : Command() {
	
	constructor(timeEntries: TimeEntries, offset: Long): this(timeEntries) {
		val now = LocalTime.now();
		_time = now.minusMinutes(offset);
	}

	public override val m_name: String = "in";
	public override val m_help: String = "";
	public override val m_alias: String? = "i";

	public override fun execute() {
		if (timeEntries.isClockedIn()) return;

		val newEntry = TimeEntry(_time);
		timeEntries.m_entries.add(newEntry);
	}

}
