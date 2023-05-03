package com.emdasoft.mysavings

import android.app.Application
import com.emdasoft.mysavings.presentation.CardItemViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun onStartNoTitleError() {
        val viewModel = CardItemViewModel(application = Application())
        viewModel.showInputTitleError.value?.let { assertFalse(it) }
    }

    @Test
    fun onStartNoShouldScreenClose() {
        val viewModel = CardItemViewModel(application = Application())
        viewModel.shouldScreenClose.value?.let { assertFalse(it) }
    }

}