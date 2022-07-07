package app.shamilton.timecardkt

import app.shamilton.timecardkt.config.Configuration

@Suppress("unused")
object Color {

	private val _config = Configuration.loadFromFile()

	// Color code strings from
	// http://www.topmudsites.com/forums/mud-coding/413-java-ansi.html
	@JvmStatic val BLACK: String = if(_config.color) { "\u001B[30m" } else { "" }
	@JvmStatic val RED: String = if(_config.color) { "\u001B[31m" } else { "" }
	@JvmStatic val GREEN: String = if(_config.color) { "\u001B[32m" } else { "" }
	@JvmStatic val YELLOW: String = if(_config.color) { "\u001B[33m" } else { "" }
	@JvmStatic val BLUE: String = if(_config.color) { "\u001B[34m" } else { "" }
	@JvmStatic val MAGENTA: String = if(_config.color) { "\u001B[35m" } else { "" }
	@JvmStatic val CYAN: String = if(_config.color) { "\u001B[36m" } else { "" }
	@JvmStatic val WHITE: String = if(_config.color) { "\u001B[37m" } else { "" }
	@JvmStatic val RESET: String = if(_config.color) { "\u001B[0m" } else { "" }

	@JvmStatic fun black(string: Any?) = colorize(BLACK, string)
	@JvmStatic fun red(string: Any?) = colorize(RED, string)
	@JvmStatic fun green(string: Any?) = colorize(GREEN, string)
	@JvmStatic fun yellow(string: Any?) = colorize(YELLOW, string)
	@JvmStatic fun blue(string: Any?) = colorize(BLUE, string)
	@JvmStatic fun magenta(string: Any?) = colorize(MAGENTA, string)
	@JvmStatic fun cyan(string: Any?) = colorize(CYAN, string)
	@JvmStatic fun white(string: Any?) = colorize(WHITE, string)

	@JvmStatic fun colorize(color: String, string: Any?) = "$color${string.toString()}$RESET"

}