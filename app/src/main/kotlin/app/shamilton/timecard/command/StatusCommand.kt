package app.shamilton.timecard.command

import app.shamilton.timecard.core.TimeEntries

class StatusCommand : ICommand {

    override val m_Name: String = "STATUS";
    override val m_Help: String = "";

    private val _timeEntries = TimeEntries.loadFromFile();

    override fun execute() {
        val clockState = if(_timeEntries.isClockedIn()) "in" else "out";
        println("Currently clocked $clockState.");
    }

}