package app.shamilton.timecard.config.cleaninterval

enum class CleanInterval(val days: Int) {
	MANUALLY(1),
	DAILY(1),
	WEEKLY(7),
	MONTHLY(30),
}