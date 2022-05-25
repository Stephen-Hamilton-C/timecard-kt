package app.shamilton.timecard

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class AppTest {

	companion object {
		private var _appInit: Boolean = false
	}

	init {
		if(!_appInit) {
			App.setArgs(arrayOf("TESTARG1", "testArg2"))
			_appInit = true
		}
	}

	@Test fun canInitArgs() {
		assertNotNull(App.getArg(0))
		assertEquals("TESTARG1", App.getArg(0))
	}

	@Test fun cannotReinitArgs() {
		assertFailsWith<IllegalStateException> { App.setArgs(arrayOf()) }
	}

	@Test fun capitalizesArgs() {
		assertEquals("TESTARG2", App.getArg(1))
	}

}