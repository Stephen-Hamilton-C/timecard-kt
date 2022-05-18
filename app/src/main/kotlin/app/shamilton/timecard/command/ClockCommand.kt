package app.shamilton.timecard.command

import app.shamilton.timecard.App
import app.shamilton.timecard.serializer.LocalTimeSerializer
import com.github.ajalt.mordant.rendering.TextColors.yellow
import com.github.ajalt.mordant.terminal.Terminal
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

abstract class ClockCommand : ICommand {

	override val m_HelpArgs: List<String> = listOf("[offset/time]")

	protected val _t = Terminal()

	protected fun now(): LocalTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES)

	protected fun getTime(): LocalTime? {
		val arg: String? = App.getArg(1)
		return if(arg == null) {
			now()
		} else if(arg.contains(":")) {
			getTimeFromInput(arg)
		} else {
			getTimeFromOffset(arg)
		}
	}

	private fun getTimeFromOffset(offset: String): LocalTime? {
		// Assume minutes offset
		try {
			val offsetMinutes: Long = offset.toLong().absoluteValue

			// Cannot clock out yesterday
			if (now().toSecondOfDay() - offsetMinutes * 60 < 0){
				_t.println(yellow("You cannot clock ${m_Name.lowercase()} before midnight! Did you forget a colon?"))
				return null
			}

			return now().minusMinutes(offsetMinutes)
		} catch(e: NumberFormatException) {
			_t.println(yellow("Unknown arguments. Use 'timecard help ${m_Name.lowercase()}' for usage."))
			return null
		}
	}

	private fun getTimeFromInput(input: String): LocalTime? {
		try {
			return LocalTimeSerializer.decodeData(input)
		} catch (e: IllegalStateException) {
			// User input error
			_t.println(yellow("Unknown arguments. Use 'timecard help ${m_Name.lowercase()}' for usage."))
			return null
		}
	}

}