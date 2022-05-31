package app.shamilton.timecardkt

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

object TestUtils {

	const val DATA_2ENTRY = "{\"entries\":[{\"startTime\":\"08:00\",\"endTime\":\"12:30\"},{\"startTime\":\"13:05\",\"endTime\":\"17:00\"}]}"
	const val DATA_2ENTRY_NOEND = "{\"entries\":[{\"startTime\":\"08:00\",\"endTime\":\"12:30\"},{\"startTime\":\"13:05\"}]}"
	const val DATA_1ENTRY = "{\"entries\":[{\"startTime\":\"08:00\",\"endTime\":\"12:30\"}]}"
	const val DATA_1ENTRY_NOEND = "{\"entries\":[{\"startTime\":\"08:00\"}]}"

	fun getPath(fileName: String): Path {
		return Paths.get("src", "test", "data", fileName)
	}

	fun createFile(fileName: String, fileContents: String): Path {
		val path = getPath(fileName)
		val file = File(path.toString())
		file.createNewFile()
		file.writeText(fileContents)

		return path
	}

}