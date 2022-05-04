package app.shamilton.adhdtimecard.core;

import com.squareup.moshi.Json;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.KotlinJsonAdapterFactory;
import app.shamilton.adhdtimecard.core.adapter.LocalTimeAdapter

class TimeEntries(
	@Json(name="entries")
	public val m_entries: MutableList<TimeEntry> = mutableListOf()
) {

	companion object {
		// KotlinJsonAdapterFactory is apparently deprecated, saying it has been moved...
		// Doesn't say where, and I can't find any docs on this. Too bad.
		private val _moshi = Moshi.Builder()
			.add(LocalTimeAdapter())
			.addLast(KotlinJsonAdapterFactory())
			.build();
			
		private val _adapter = _moshi.adapter(TimeEntries::class.java);
		private val _filePath: String = "";
		private val _fileName: String = "adhd-timecard_${LocalDate.now()}.json";

		@JvmStatic
		public fun loadFromFile(): TimeEntries {
			val file = File(Paths.get(_filePath, _fileName).toString());
			if(file.exists())
				return _adapter.fromJson(file.readText()) ?: TimeEntries();
			return TimeEntries();
		}
	}

	public fun saveToFile() {
		val json: String = _adapter.toJson(this);
		val file = File(Paths.get(_filePath, _fileName).toString());
		file.createNewFile();
		file.writeText(json);
	}

	public fun isClockedIn(): Boolean = m_entries.last().m_endTime == null;

	public fun isClockedOut(): Boolean = !isClockedIn();

	public override fun toString(): String {
		val entryStrings: MutableList<String> = mutableListOf();
		for(entry in m_entries) {
			entryStrings.add(entry.toString());
		}
		return entryStrings.joinToString();
	}

}
