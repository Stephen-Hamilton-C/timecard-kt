package app.shamilton.timecardkt

@Suppress("unused")
object Color {

	// Color code strings from
	// http://www.topmudsites.com/forums/mud-coding/413-java-ansi.html
	const val BLACK: String = "\u001B[30m"
	const val RED: String = "\u001B[31m"
	const val GREEN: String = "\u001B[32m"
	const val YELLOW: String = "\u001B[33m"
	const val BLUE: String = "\u001B[34m"
	const val MAGENTA: String = "\u001B[35m"
	const val CYAN: String = "\u001B[36m"
	const val WHITE: String = "\u001B[37m"
	const val RESET: String = "\u001B[0m"

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