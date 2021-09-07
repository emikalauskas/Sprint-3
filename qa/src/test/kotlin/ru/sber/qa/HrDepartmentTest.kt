package ru.sber.qa

import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

internal class HrDepartmentTest {
    private val certificateRequest = mockk<CertificateRequest>()
    private val hrDepartment = mockk<HrDepartment>()
    private val certificate = mockk<Certificate>()

    @Test
    fun `receiveRequest() with WeekendDayException when currentDayOfWeek is SUNDAY`() {
        every { hrDepartment.clock } returns Clock.fixed(Instant.parse("2021-09-11T00:00:00Z"), ZoneOffset.UTC)
        every { certificateRequest.certificateType } returns CertificateType.LABOUR_BOOK
        assertThrows<WeekendDayException> {
            HrDepartment.receiveRequest(certificateRequest)
        }
    }

    @Test
    fun `receiveRequest() with NotAllowReceiveRequestException when certificateType is NDFL and currentDayOfWeek is TUESDAY`() {
        every { hrDepartment.clock } returns Clock.fixed(Instant.parse("2021-09-07T00:00:00Z"), ZoneOffset.UTC)
        every { certificateRequest.certificateType } returns CertificateType.NDFL
        assertThrows<NotAllowReceiveRequestException> {
            HrDepartment.receiveRequest(certificateRequest)
        }
    }

    @Test
    fun `receiveRequest() with NotAllowReceiveRequestException when certificateType is LABOUR_BOOK and currentDayOfWeek is WEDNESDAY`() {
        every { hrDepartment.clock } returns Clock.fixed(Instant.parse("2021-09-08T00:00:00Z"), ZoneOffset.UTC)
        every { certificateRequest.certificateType } returns CertificateType.LABOUR_BOOK
        assertThrows<NotAllowReceiveRequestException> {
            HrDepartment.receiveRequest(certificateRequest)
        }
    }

    @Test
    fun `receiveRequest() with no exceptions`() {
        every { hrDepartment.clock } returns Clock.fixed(Instant.parse("2021-09-08T00:00:00Z"), ZoneOffset.UTC)
        every { certificateRequest.certificateType } returns CertificateType.NDFL
        assertDoesNotThrow {
            HrDepartment.receiveRequest(certificateRequest)
        }
    }

    @Test
    fun `processNextRequest() with no exceptions`() {
        val hrEmployeeNumber = 99L

        every { hrDepartment.clock } returns Clock.fixed(Instant.parse("2021-09-08T00:00:00Z"), ZoneOffset.UTC)
        every { certificateRequest.certificateType } returns CertificateType.NDFL
        every { certificateRequest.process(hrEmployeeNumber) } returns certificate

        HrDepartment.receiveRequest(certificateRequest)

        assertDoesNotThrow {
            HrDepartment.processNextRequest(hrEmployeeNumber)
        }
    }

    @AfterEach
    fun unmockk() {
        unmockkAll()
    }
}