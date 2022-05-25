package app.shamilton.timecard.config

import com.charleskorn.kaml.Yaml
import io.github.erayerdin.kappdirs.AppDirsFactory
import io.github.erayerdin.kappdirs.appdirs.AppDirs
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Serializable
class Configuration (
	var clean_interval: CleanInterval = CleanInterval.WEEKLY
) {
	companion object {
		private val _appDirs: AppDirs = AppDirsFactory.getInstance()
		val FILEPATH: String = _appDirs.getUserConfigDir("timecard", "", "stephen-hamilton-c").toString()
		const val FILENAME: String = "config.yml"

		private var _cached: Configuration? = null

		@JvmStatic fun loadFromFile(path: Path = Paths.get(FILEPATH, FILENAME)): Configuration {
			if(_cached != null && path.toString() == Paths.get(FILEPATH, FILENAME).toString()) return _cached as Configuration

			var configuration = Configuration()
			val file = File(path.toString())
			if (file.exists()) {
				val data = file.readText()
				val deserializedConfig: Configuration? = Yaml.default.decodeFromString(data)
				configuration = deserializedConfig ?: configuration
			}
			if (path.toString() == Paths.get(FILEPATH, FILENAME).toString()) {
				_cached = configuration
			}
			return configuration
		}
	}

	fun saveToFile(path: Path = Paths.get(FILEPATH, FILENAME)) {
		val data: String = Yaml.default.encodeToString(this)
		Files.createDirectories(path.parent)
		val file = File(path.toString())
		file.createNewFile()
		file.writeText(data)
	}
}