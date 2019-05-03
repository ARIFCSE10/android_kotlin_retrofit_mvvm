package com.beliefit.tmq.mybookshop

import com.beliefit.tmq.mybookshop.util.EmailValidator
import org.junit.After
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    lateinit var emailValidator: EmailValidator

    @Before
    fun setUp() {
        emailValidator = EmailValidator()
    }

    @After
    fun tearDown() {
    }

}
