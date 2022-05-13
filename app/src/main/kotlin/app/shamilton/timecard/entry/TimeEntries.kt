package app.shamilton.timecard.core

import app.shamilton.timecard.App
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import net.harawata.appdirs.AppDirs
import net.harawata.appdirs.AppDirsFactory
import java.nio.file.Files
import java.nio.file.Path

@Serializable
class TimeEntries(
	@SerialName("entries")
	val m_Entries: MutableList<TimeEntry> = mutableListOf()
) {

	companion object {
		private val _appDirs: AppDirs = AppDirsFactory.getInstance()
		val FILEPATH: String = _appDirs.getUserDataDir(App.NAME, "", App.AUTHOR)
		val FILENAME: String = "timecard_${LocalDate.now()}.json"

		@JvmStatic fun loadFromFile(path: Path = Paths.get(FILEPATH, FILENAME)): TimeEntries {
			val file = File(path.toString())
			if (file.exists()) {
				val data = file.readText()
				val deserializedEntries: TimeEntries? = Json.decodeFromString(data)
				return deserializedEntries ?: TimeEntries()
			}
			return TimeEntries()
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
