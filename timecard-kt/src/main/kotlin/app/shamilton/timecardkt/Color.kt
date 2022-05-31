package app.shamilton.timecardkt

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

	fun black(string: String) = colorize(BLACK, string)
	fun red(string: String) = colorize(RED, string)
	fun green(string: String) = colorize(GREEN, string)
	fun yellow(string: String) = colorize(YELLOW, string)
	fun blue(string: String) = colorize(BLUE, string)
	fun magenta(string: String) = colorize(MAGENTA, string)
	fun cyan(string: String) = colorize(CYAN, string)
	fun white(string: String) = colorize(WHITE, string)

	fun colorize(color: String, string: String) = "$color$string$RESET"

}