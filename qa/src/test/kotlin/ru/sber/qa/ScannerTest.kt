package ru.sber.qa

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

internal class ScannerTest {
    @Test
    fun `getScanData() with ScanTimeoutException`() {
        mockkObject(Random)
        every { Random.nextLong(5000L, 15000L) } returns 10001L
        assertThrows<ScanTimeoutException> {
            Scanner.getScanData()
        }
    }

    @Test
    fun `getScanData() without ScanTimeoutException and returned right value`() {
        val expectedValue = Random.nextBytes(100)

        mockkObject(Random)
        every { Random.nextLong(5000L, 15000L) } returns 10000L
        every { Random.nextBytes(100) } returns expectedValue

        assertArrayEquals(expectedValue, Scanner.getScanData())
    }

    @AfterEach
    fun unmockk() {
        unmockkAll()
    }
}