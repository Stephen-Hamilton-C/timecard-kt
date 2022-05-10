package app.shamilton.timecard.command

import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import app.shamilton.timecard.core.TimeEntries

class StatusCommand : ICommand {

    override val m_Name: String = "STATUS"

    private val _t = Terminal()

    override fun execute() {
        val timeEntries = TimeEntries.loadFromFile()
        val clockState = if(timeEntries.isClockedIn()) green("in") else red("out")
        _t.println("Currently clocked $clockState.")

        // TODO: Print how long worked
        // TODO: Print how many hours to the nearest quarter
    }

}