package com.beliefit.tmq.mybookshop

import android.content.Context
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.beliefit.tmq.mybookshop.util.EmailValidator
import com.beliefit.tmq.mybookshop.util.PasswordValidator
import com.beliefit.tmq.mybookshop.util.SharedPrefUtil
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    lateinit var mContext: Context

    @Before
    fun setUp() {
        mContext = InstrumentationRegistry.getTargetContext()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.beliefit.tmq.mybookshop", appContext.packageName)
    }

    @Test
    fun testEmail() {
        var empty = ""
        var onlyname = "badsha"
        var onlydomain = "gmail.com"
        var wrongFormat = "asb.com@"
        var doubledot = "badsha@gmail..com"
        var missingdomain = "hello@abc"
        var twoat = "bad@sha@asd.com"
        var perfect = "badsha.bdmap@gmail.com"

        Assert.assertTrue(EmailValidator().validate(perfect))
        Assert.assertFalse(EmailValidator().validate(twoat))
        Assert.assertFalse(EmailValidator().validate(missingdomain))
        Assert.assertFalse(EmailValidator().validate(doubledot))
        Assert.assertFalse(EmailValidator().validate(wrongFormat))
        Assert.assertFalse(EmailValidator().validate(onlydomain))
        Assert.assertFalse(EmailValidator().validate(onlyname))
        Assert.assertFalse(EmailValidator().validate(empty))
    }

    @Test
    fun testPassword() {
        var empty = ""
        var lessthansix = "abc"
        var perfect = "badsha"

        Assert.assertTrue(PasswordValidator().validate(perfect))
        Assert.assertFalse(PasswordValidator().validate(lessthansix))
        Assert.assertFalse(PasswordValidator().validate(empty))
    }

    @Test
    fun testSharedPref() {
        var name = "Mohammed Badsha Alamgir"
        var email = "badsha.bdmap@gmail.com"
        var isLogged = true

        SharedPrefUtil.setUserNamePref(name, mContext)
        Assert.assertEquals(name, SharedPrefUtil.getUserNamePref(mContext))

        SharedPrefUtil.setUserEmailPref(email, mContext)
        Assert.assertEquals(email, SharedPrefUtil.getUserEmailPref(mContext))

        SharedPrefUtil.setUserHasLoggedInPref(isLogged, mContext)
        Assert.assertEquals(isLogged, SharedPrefUtil.getUserHasLoggedInPref(mContext))

    }

}
