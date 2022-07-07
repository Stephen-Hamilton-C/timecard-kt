package app.shamilton.timecardkt.config

import app.shamilton.timecardkt.App
import io.github.erayerdin.kappdirs.AppDirsFactory
import io.github.erayerdin.kappdirs.appdirs.AppDirs
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import com.charleskorn.kaml.Yaml
import java.io.File
import java.nio.file.Files
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.nio.file.Paths

@Serializable
class Configuration (
	var clean_interval: CleanInterval = CleanInterval.WEEKLY,
	var time_format: TimeFormat = TimeFormat.WRITTEN,
	var hour_24: Hour24 = Hour24.FALSE,
	// Default to false on Windows because command prompt is poopy >:|
	var color: Boolean = !System.getProperty("os.name").contains("Windows"),
) {
	companion object {
		private val _appDirs: AppDirs = AppDirsFactory.getInstance()
		val FILEPATH: Path = getFilePath()
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

		private fun isValidPath(path: String?): Boolean {
			if(path.isNullOrBlank()) return false
			try {
				Paths.get(path)
			} catch(e: InvalidPathException) {
				return false
			}
			return true
		}

		private fun getFilePath(): Path {
			if(isValidPath(System.getenv("XDG_CONFIG_HOME"))){
				val configHome = Paths.get(System.getenv("XDG_CONFIG_HOME"))
				return Paths.get(configHome.toString(), App.NAME)
			}
			return _appDirs.getUserConfigDir(App.NAME, "", App.AUTHOR)
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