package app.shamilton.timecard.core.config

import app.shamilton.timecard.App
import com.charleskorn.kaml.Yaml
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.harawata.appdirs.AppDirs
import net.harawata.appdirs.AppDirsFactory
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Serializable
class Configuration (
	@SerialName("target_working_hours")
	var targetWorkingHours: Double = 8.0,
	@SerialName("typical_break_hours")
	var typicalBreakHours: Double = 1.0
) {
	companion object {
		private val _appDirs: AppDirs = AppDirsFactory.getInstance()
		val FILEPATH: String = _appDirs.getUserConfigDir(App.NAME, "", App.AUTHOR)
		const val FILENAME: String = "config.yml"

		@JvmStatic fun loadFromFile(path: Path = Paths.get(FILEPATH, FILENAME)): Configuration {
			val file = File(path.toString())
			if (file.exists()) {
				val data = file.readText()
				val deserializedConfig: Configuration? = Yaml.default.decodeFromString(data)
				return deserializedConfig ?: Configuration()
			}
			return Configuration()
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