package app.shamilton.timecard.core;

import app.shamilton.timecard.App
import com.squareup.moshi.Json;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;
import app.shamilton.timecard.core.adapter.LocalTimeAdapter;
import com.squareup.moshi.JsonAdapter
import net.harawata.appdirs.AppDirs
import net.harawata.appdirs.AppDirsFactory

class TimeEntries(
	@Json(name="entries")
	val m_Entries: MutableList<TimeEntry> = mutableListOf()
) {

	companion object {
		var _app: App? = null;
		var _moshi: Moshi? = null;
		var _adapter: JsonAdapter<TimeEntries>? = null;
		var _appDirs: AppDirs? = null;
		var _filePath: String? = null;
		var _fileName: String? = null;

		@JvmStatic fun loadFromFile(): TimeEntries {
			_app = App();
			_moshi = Moshi.Builder()
				.add(LocalTimeAdapter())
				.addLast(KotlinJsonAdapterFactory())
				.build();
			_adapter = _moshi?.adapter(TimeEntries::class.java);
			_appDirs = AppDirsFactory.getInstance();
			_filePath = _appDirs?.getUserDataDir(_app?.NAME, "", _app?.AUTHOR);
			_fileName = "timecard_${LocalDate.now()}.json";

			val file = File(Paths.get(_filePath!!, _fileName!!).toString());
			if (file.exists()) {
				val deserializedEntries: TimeEntries? = _adapter!!.fromJson(file.readText());
				return deserializedEntries ?: TimeEntries();
			}
			return TimeEntries();
		}
	}

	fun saveToFile() {
		val json: String = _adapter!!.toJson(this);
		val file = File(Paths.get(_filePath!!, _fileName!!).toString());
		file.createNewFile();
		file.writeText(json);
	}

	fun isClockedIn(): Boolean = if(m_Entries.isNotEmpty()) m_Entries.last().m_EndTime == null else false;

	fun isClockedOut(): Boolean = !isClockedIn();

	override fun toString(): String {
		val entryStrings: MutableList<String> = mutableListOf();
		for(entry in m_Entries) {
			entryStrings.add(entry.toString());
		}
		return entryStrings.joinToString();
	}

}
