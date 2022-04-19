package app.shamilton.adhdtimecard.core;

import com.squareup.moshi.Json;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime
import java.time.ZoneOffset
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.KotlinJsonAdapterFactory;

class TimeEntries(
	@Json(name="entries") val m_entries: MutableList<TimeEntry> = mutableListOf()
) {

	companion object {
		private val _moshi = Moshi.Builder()
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

	public fun isClockedIn(): Boolean = m_entries.last().m_endTime.toInt() == 0;

	public fun isClockedOut(): Boolean = !isClockedIn();

	public fun clockIn(time: LocalDateTime = LocalDateTime.now()): Boolean {
		if(isClockedIn()) return false;

		val newEntry = TimeEntry(time.toEpochSecond(ZoneOffset.UTC));
		m_entries.add(newEntry);
		return true;
	}

	public fun clockOut(time: LocalDateTime = LocalDateTime.now()): Boolean {
		if(isClockedOut()) return false;

		m_entries.last().m_endTime = time.toEpochSecond(ZoneOffset.UTC);
		return true;
	}

	public override fun toString(): String {
		val entryStrings: MutableList<String> = mutableListOf();
		for(entry in m_entries) {
			entryStrings.add(entry.toString());
		}
		return entryStrings.joinToString();
	}

}
