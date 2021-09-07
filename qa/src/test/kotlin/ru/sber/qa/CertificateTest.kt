package ru.sber.qa

import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CertificateTest {
    private val certificateRequest = mockk<CertificateRequest>()
    private val processedBy = 333L
    private val data = byteArrayOf(1, 2, 3)
    private val certificate = Certificate(certificateRequest, processedBy, data)

    @Test
    fun `certificateRequest has a right value`() {
        assertEquals(certificateRequest, certificate.certificateRequest)
    }

    @Test
    fun `processedBy has a right value`() {
        assertEquals(processedBy, certificate.processedBy)
    }

    @Test
    fun `data has a right value`() {
        assertEquals(data, certificate.data)
    }

    @AfterEach
    fun unmockk() {
        unmockkAll()
    }
}