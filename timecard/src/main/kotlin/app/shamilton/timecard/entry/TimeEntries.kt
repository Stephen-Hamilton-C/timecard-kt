package app.shamilton.timecard.entry

import io.github.erayerdin.kappdirs.AppDirsFactory
import io.github.erayerdin.kappdirs.appdirs.AppDirs
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import java.nio.file.Files
import java.nio.file.Path

@Serializable
class TimeEntries(
	@SerialName("entries")
	val m_Entries: MutableList<TimeEntry> = mutableListOf()
) {

	companion object {
		private val _appDirs: AppDirs = AppDirsFactory.getInstance()
		val FILEPATH: String = _appDirs.getUserDataDir("timecard", "", "stephen-hamilton-c").toString()
		val FILENAME: String = "timecard_${LocalDate.now()}.json"

		private var _cached: TimeEntries? = null

		@JvmStatic fun loadFromFile(path: Path = Paths.get(FILEPATH, FILENAME)): TimeEntries {
			if(_cached != null && path.toString() == Paths.get(FILEPATH, FILENAME).toString()) return _cached as TimeEntries

			var timeEntries = TimeEntries()
			val file = File(path.toString())
			if (file.exists()) {
				val data = file.readText()
				val deserializedEntries: TimeEntries? = Json.decodeFromString(data)
				timeEntries = deserializedEntries ?: timeEntries
			}
			if(path.toString() == Paths.get(FILEPATH, FILENAME).toString()) {
				_cached = timeEntries
			}
			return timeEntries
		}
	}

	fun saveToFile(path: Path = Paths.get(FILEPATH, FILENAME)) {
		val data: String = Json.encodeToString(this)
		Files.createDirectories(path.parent)
		val file = File(path.toString())
		file.createNewFile()
		file.writeText(data)
	}

	fun deleteFile() {
		val file = File(Paths.get(FILEPATH, FILENAME).toString())
		file.delete()
	}

	fun isClockedIn(): Boolean = if(m_Entries.isNotEmpty()) m_Entries.last().endTime == null else false

	fun isClockedOut(): Boolean = !isClockedIn()

	override fun toString(): String {
		val entryStrings: MutableList<String> = mutableListOf()
		for(entry in m_Entries) {
			entryStrings.add(entry.toString())
		}
		return entryStrings.joinToString()
	}

}
