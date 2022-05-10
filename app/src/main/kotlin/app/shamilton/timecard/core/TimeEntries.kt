package app.shamilton.timecard.core

import app.shamilton.timecard.App
import com.squareup.moshi.Json
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import app.shamilton.timecard.core.adapter.LocalTimeAdapter
import com.squareup.moshi.JsonAdapter
import net.harawata.appdirs.AppDirs
import net.harawata.appdirs.AppDirsFactory

class TimeEntries(
	@Json(name="entries")
	val m_Entries: MutableList<TimeEntry> = mutableListOf()
) {

	companion object {
		private val _app = App()
		private val _moshi: Moshi = Moshi.Builder()
			.add(LocalTimeAdapter())
			.addLast(KotlinJsonAdapterFactory())
			.build()
		private val _adapter: JsonAdapter<TimeEntries> = _moshi.adapter(TimeEntries::class.java)
		private val _appDirs: AppDirs = AppDirsFactory.getInstance()
		private val _filePath: String = _appDirs.getUserDataDir(_app.NAME, "", _app.AUTHOR)
		private val _fileName: String = "timecard_${LocalDate.now()}.json"

		@JvmStatic fun loadFromFile(): TimeEntries {
			val file = File(Paths.get(_filePath, _fileName).toString())
			if (file.exists()) {
				val deserializedEntries: TimeEntries? = _adapter.fromJson(file.readText())
				return deserializedEntries ?: TimeEntries()
			}
			return TimeEntries()
		}
	}

	fun saveToFile() {
		val json: String = _adapter.toJson(this)
		val file = File(Paths.get(_filePath, _fileName).toString())
		file.createNewFile()
		file.writeText(json)
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
