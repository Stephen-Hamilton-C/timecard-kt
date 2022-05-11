package app.shamilton.timecard.command

import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal
import app.shamilton.timecard.core.TimeEntries
import app.shamilton.timecard.core.TimeEntry
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class StatusCommand : ICommand {

    override val m_Name: String = "STATUS"
    override val m_Help: String = "" // TODO

    private val _t = Terminal()

    private fun getTimeWorked(timeEntries: TimeEntries): LocalTime {
        var timeWorked = LocalTime.of(0, 0)
        for (entry: TimeEntry in timeEntries.m_Entries) {
            val startTime: LocalTime = entry.startTime
            val endTime: LocalTime = entry.endTime ?: LocalTime.now().truncatedTo(ChronoUnit.MINUTES)

            val startTimeMinutes: Int = startTime.hour * 60 + startTime.minute
            val difference: LocalTime = endTime.minusMinutes(startTimeMinutes.toLong())
            val differenceMinutes: Int = difference.hour * 60 + difference.minute

            timeWorked = timeWorked.plusMinutes(differenceMinutes.toLong())
        }

        return timeWorked
    }

    override fun execute() {
        val timeEntries = TimeEntries.loadFromFile()
        if(timeEntries.m_Entries.isEmpty()) {
            _t.println(yellow("You haven't clocked in yet today! Use 'timecard in' to clock in."))
            return
        }

        val clockState = if(timeEntries.isClockedIn()) "in" else "out"
        val color = if(timeEntries.isClockedIn()) green else red
        _t.println(color("Currently clocked $clockState."))


        // Print how long worked
        val timeWorked: LocalTime = getTimeWorked(timeEntries)
        val minutes: String = if(timeWorked.minute == 1) "minute" else "minutes"
        if(timeWorked.hour > 0){
            val hours: String = if(timeWorked.hour == 1) "hour" else "hours"
            println("Worked for ${timeWorked.hour} $hours and ${timeWorked.minute} $minutes")
        } else {
            println("Worked for ${timeWorked.minute} $minutes")
        }

        // TODO: Print how many hours to the nearest quarter
    }

}