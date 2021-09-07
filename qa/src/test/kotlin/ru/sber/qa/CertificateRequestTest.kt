package ru.sber.qa

import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CertificateRequestTest {
    private val employeeNumber = 55L
    private val certificateType = CertificateType.NDFL
    private val hrEmployeeNumber = 77L
    private val data = byteArrayOf(1, 2, 3)
    private val certificateRequest = CertificateRequest(employeeNumber, certificateType)

    @Test
    fun `process() created certificate with right values`() {
        mockkObject(Scanner)
        every { Scanner.getScanData() } returns data

        val certificate = certificateRequest.process(hrEmployeeNumber)

        assertEquals(certificateRequest, certificate.certificateRequest)
        assertEquals(hrEmployeeNumber, certificate.processedBy)
        assertEquals(data, certificate.data)

        verify { Scanner.getScanData() }
        confirmVerified(Scanner)
    }

    @Test
    fun `employeeNumber has a right value`() {
        assertEquals(employeeNumber, certificateRequest.employeeNumber)
    }

    @Test
    fun `certificateType has a right value`() {
        assertEquals(certificateType, certificateRequest.certificateType)
    }

    @AfterEach
    fun unmockk() {
        unmockkAll()
    }
}