package com.example.converter

import android.content.Context
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.example.converter.helper.MainLocale.getCountryCode
import com.example.converter.helper.MainLocale.getSymbolCurrency
import com.example.converter.helper.Utiltity.isNetworkConnected
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import androidx.test.runner.AndroidJUnitRunner
import com.example.converter.helper.MainLocale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {
    lateinit var instrumentationContext: Context

    @Test
    fun getSymbolCurrency_isCorrect() {
        assertEquals("RUB", getSymbolCurrency("RU"))
    }

    @Test
    fun getCountryCode_isCorrect() {
        assertEquals("EUR", MainLocale.getCountryCode("Australia"))
    }

}