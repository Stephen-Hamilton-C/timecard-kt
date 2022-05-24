package app.shamilton.timecard.config

enum class CleanInterval(val days: Int) {
	MANUALLY(1),
	DAILY(1),
	WEEKLY(7),
	MONTHLY(30);

	companion object {
		@JvmStatic fun get(name: String?): CleanInterval? {
			if (name == null) return null
			return try {
				CleanInterval.valueOf(name.uppercase())
			} catch (e: IllegalArgumentException) {
				null
			}
		}
	}
}