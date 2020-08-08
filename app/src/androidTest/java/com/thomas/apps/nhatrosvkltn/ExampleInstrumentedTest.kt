package com.thomas.apps.nhatrosvkltn

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.thomas.apps.nhatrosvkltn.utils.convertPrice
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.thomas.apps.nhatrosvkltn", appContext.packageName)
    }

    @Test
    fun convertPrice_isCorrect() {
        assertEquals("3,000,000", convertPrice("3000000"))
    }
}
