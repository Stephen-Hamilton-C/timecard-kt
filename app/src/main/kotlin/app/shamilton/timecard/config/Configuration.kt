package app.shamilton.timecard.config

import com.charleskorn.kaml.Yaml
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

) {
	companion object {
		private val _appDirs: AppDirs = AppDirsFactory.getInstance()
		val FILEPATH: String = _appDirs.getUserConfigDir("timecard", "", "stephen-hamilton-c")
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