package app.shamilton.timecardkt.config

class ColorConfig : Config() {

	override val m_Name: String = "COLOR"
	override var m_Value: String
		get() = _config.color.toString().uppercase()
		set(value) {
			_config.color = value.toBoolean()
			_config.saveToFile()
		}

	override val m_PossibleValues: List<String> = listOf("TRUE", "FALSE")
	override val m_Help: String = "Determines whether or not to make some text colorful. Some terminals may not support this."

}