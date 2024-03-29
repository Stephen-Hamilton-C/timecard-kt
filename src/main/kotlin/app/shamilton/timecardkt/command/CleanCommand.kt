package app.shamilton.timecardkt.command

import app.shamilton.timecardkt.Color.cyan
import app.shamilton.timecardkt.config.CleanInterval
import app.shamilton.timecardkt.config.Configuration
import app.shamilton.timecardkt.entry.TimeEntries
import java.io.File
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeParseException

class CleanCommand : IAutoCommand {

	override val m_Name: String = "CLEAN"
	override val m_Help: String = "Removes old timecard files."
	override val m_DetailedHelp: String? = null
	override val m_HelpArgs: List<String> = listOf()

	private var _executed: Boolean = false

	override fun autoRun() {
		if (_executed) return

		val config = Configuration.loadFromFile()
		if (config.clean_interval != CleanInterval.MANUALLY) {
			cleanUp(true, config)
		}
	}

	override fun execute() {
		cleanUp(false, Configuration.loadFromFile())
	}

	private fun cleanUp(autorun: Boolean, config: Configuration) {
		val today = LocalDate.now()
		val dataDir: File = TimeEntries.FILEPATH.toFile()
		val timecardFiles: Array<File> = dataDir.listFiles { file ->
			file.name.startsWith("timecard_") && file.name.endsWith(".json")
		} ?: arrayOf()

		for (timecardFile in timecardFiles) {
			try {
				val timecardDateString: String = timecardFile.nameWithoutExtension.substring(9)
				val timecardDate = LocalDate.parse(timecardDateString)
				val difference = Period.between(timecardDate, today)
				if (difference.days >= config.clean_interval.days) {
					println(cyan("Removed old timecard for $timecardDateString"))
					timecardFile.delete()
				}
			} catch (e: DateTimeParseException) {
				// Likely not a timecard file
				continue
			}
		}

		val days = if(config.clean_interval.days == 1) "day" else "days"
		if(!autorun) {
			println("Cleaned up timecards older than ${config.clean_interval.days} $days")
		}

		_executed = true
	}

}