package app.shamilton.timecardkt.config

import app.shamilton.timecardkt.App
import app.shamilton.timecardkt.config.cleaninterval.CleanInterval
import app.shamilton.timecardkt.config.hour24.Hour24
import app.shamilton.timecardkt.config.timeformat.TimeFormat
import io.github.erayerdin.kappdirs.AppDirsFactory
import io.github.erayerdin.kappdirs.appdirs.AppDirs
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import com.charleskorn.kaml.Yaml
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@Serializable
class Configuration (
	var clean_interval: CleanInterval = CleanInterval.WEEKLY,
	var time_format: TimeFormat = TimeFormat.WRITTEN,
	var hour_24: Hour24 = Hour24.FALSE,
) {
	companion object {
		private val _appDirs: AppDirs = AppDirsFactory.getInstance()
		val FILEPATH: Path = _appDirs.getUserConfigDir(App.NAME, "", App.AUTHOR)
		const val FILENAME: String = "config.yml"

		private var _cached: Configuration? = null

		@JvmStatic fun loadFromFile(path: Path = FILEPATH.resolve(FILENAME)): Configuration {
			if(_cached != null && path == FILEPATH.resolve(FILENAME)) return _cached as Configuration

			var configuration = Configuration()
			val file: File = path.toFile()
			if (file.exists()) {
				val data = file.readText()
				val deserializedConfig: Configuration? = Yaml.default.decodeFromString(data)
				configuration = deserializedConfig ?: configuration
			}
			if (path == FILEPATH.resolve(FILENAME)) {
				_cached = configuration
			}
			return configuration
		}
	}

	fun saveToFile(path: Path = FILEPATH.resolve(FILENAME)) {
		val data: String = Yaml.default.encodeToString(this)
		Files.createDirectories(path.parent)
		val file: File = path.toFile()
		file.createNewFile()
		file.writeText(data)
	}
}