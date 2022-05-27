package app.shamilton.timecard.config

abstract class Config {

	abstract val m_Name: String
	abstract var m_Value: String
	abstract val m_PossibleValues: List<String>

	protected val _config = Configuration.loadFromFile()

}