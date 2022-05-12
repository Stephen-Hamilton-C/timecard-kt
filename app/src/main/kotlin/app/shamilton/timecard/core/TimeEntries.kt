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

@Serializable
class TimeEntries(
	@SerialName("entries")
	val m_Entries: MutableList<TimeEntry> = mutableListOf()
) {

	companion object {
		private val _app = App()
		private val _appDirs: AppDirs = AppDirsFactory.getInstance()
		val filePath: String = _appDirs.getUserDataDir(_app.NAME, "", _app.AUTHOR)
		val fileName: String = "timecard_${LocalDate.now()}.json"

		@JvmStatic fun loadFromFile(): TimeEntries {
			val file = File(Paths.get(filePath, fileName).toString())
			if (file.exists()) {
				val deserializedEntries: TimeEntries? = Json.decodeFromString(file.readText())
				return deserializedEntries ?: TimeEntries()
			}
			return TimeEntries()
		}
	}

	fun saveToFile() {
		val data: String = Json.encodeToString(this)
		Files.createDirectories(Paths.get(filePath))
		val file = File(Paths.get(filePath, fileName).toString())
		file.createNewFile()
		file.writeText(data)
	}

	fun deleteFile() {
		val file = File(Paths.get(filePath, fileName).toString())
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
