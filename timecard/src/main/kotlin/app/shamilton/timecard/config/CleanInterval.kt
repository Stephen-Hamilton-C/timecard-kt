package app.shamilton.timecard.config

enum class CleanInterval(val days: Int) {
	MANUALLY(1),
	DAILY(1),
	WEEKLY(7),
	MONTHLY(30),
}