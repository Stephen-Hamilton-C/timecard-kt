package app.shamilton.timecard.config

enum class CleanInterval(val days: Int, val automatic: Boolean = true) {
	MANUALLY(1, false),
	DAILY(1),
	WEEKLY(7),
	MONTHLY(30),
}