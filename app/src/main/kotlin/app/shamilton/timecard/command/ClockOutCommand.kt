package app.shamilton.timecard.command;

import app.shamilton.timecard.core.TimeEntries;
import app.shamilton.timecard.core.TimeEntry;
import com.github.ajalt.mordant.rendering.TextColors.red;
import com.github.ajalt.mordant.terminal.Terminal
import java.time.LocalTime

class ClockOutCommand : ICommand {
	
	override val m_Name: String = "OUT";

	private val _t = Terminal();

	override fun execute() {
		val timeEntries = TimeEntries.loadFromFile();
		if (timeEntries.isClockedOut()) {
			_t.println(red("Already clocked out!"));
			println("Use 'timecard in' to clock in.");
			return
		};

		val lastEntry: TimeEntry = timeEntries.m_Entries.last();
		lastEntry.endTime = LocalTime.now();

		timeEntries.saveToFile();
	}

}
