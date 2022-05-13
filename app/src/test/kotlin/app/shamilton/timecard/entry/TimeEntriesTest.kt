package app.shamilton.timecard.entry

import org.junit.Test
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalTime
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class TimeEntriesTest {

	private val _testData = "{\"entries\":[{\"startTime\":\"08:00\",\"endTime\":\"12:30\"},{\"startTime\":\"13:05\"}]}"

	private fun getPath(fileName: String): Path {
		return Paths.get("src", "test", "data", fileName)
	}

	private fun createFile(fileName: String, fileContents: String): Path {
		val path = getPath(fileName)
		val file = File(path.toString())
		file.createNewFile()
		file.writeText(fileContents)

		return path
	}

	@Test fun canLoadFromJson() {
		val testDataPath: Path = createFile("timecard_can-load-from-json.json", _testData)
		val timeEntries = TimeEntries.loadFromFile(testDataPath)
		val entries: List<TimeEntry> = timeEntries.m_Entries
		assert(entries.isNotEmpty())

		val firstEntry: TimeEntry = entries.first()
		assertEquals(firstEntry.startTime.hour, 8)
		assertEquals(firstEntry.startTime.minute, 0)
		assertNotNull(firstEntry.endTime)
		assertEquals(firstEntry.endTime!!.hour, 12)
		assertEquals(firstEntry.endTime!!.minute, 30)

		val lastEntry: TimeEntry = entries.last()
		assertEquals(lastEntry.startTime.hour, 13)
		assertEquals(lastEntry.startTime.minute, 5)
		assertNull(lastEntry.endTime)
	}

	@Test fun canSaveToJson() {
		val timeEntries = TimeEntries(mutableListOf(
			TimeEntry(LocalTime.of(8, 0), LocalTime.of(12, 30)),
			TimeEntry(LocalTime.of(13, 5))
		))
		val path = getPath("timecard_can-save-to-json.json")
		timeEntries.saveToFile(path)
		val file = File(path.toString())
		assertTrue { file.exists() }
	}

	@Test fun knowsWhenClockedOut() {
		val timeEntries = TimeEntries(mutableListOf(
			TimeEntry(LocalTime.of(8, 0), LocalTime.of(12, 30))
		))
		assertTrue { timeEntries.isClockedOut() }
	}

	@Test fun knowsWhenClockedIn() {
		val timeEntries = TimeEntries(mutableListOf(
			TimeEntry(LocalTime.of(8, 0))
		))
		assertTrue { timeEntries.isClockedIn() }
	}

	@Test fun reportsAsClockedOutWhenEmpty() {
		val timeEntries = TimeEntries()
		assertTrue { timeEntries.isClockedOut() }
	}

	@Test fun failsOnInvalidJson() {
		val badData = _testData.substring(0.._testData.length - 10)
		val badDataPath: Path = createFile("timecard_fails-on-invalid-json.json", badData)
		assertFails {
			TimeEntries.loadFromFile(badDataPath)
		}
	}

	@Test fun failsOnInvalidTimeData() {
		val badData = "{\"entries\":[{\"startTime\":\"08:00\",\"endTime\":\"12:30\"},{\"startTime\":\"1305\"}]}"
		val badDataPath: Path = createFile("timecard_fails-on-invalid-time-data.json", badData)
		assertFailsWith<IllegalStateException> {
			TimeEntries.loadFromFile(badDataPath)
		}
	}
}