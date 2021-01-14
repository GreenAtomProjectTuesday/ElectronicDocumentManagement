package electonic.document.management.service;

import electonic.document.management.model.report.Report;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before-report.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ReportServiceTest {
    @Autowired
    private ReportService reportService;

    @Test
    void checkReport() {
        Report report = reportService.getReportByTaskName("eee");
        assertEquals(3, report.getId());
        assertEquals(LocalDateTime.of(2020, 1, 8, 10, 23
                , 54)
                , report.getCreationDate());
        assertEquals(LocalDateTime.of(2020, 2, 18, 12, 0
                , 0)
                , report.getExpiryDate());
        assertEquals("eee", report.getName());
        assertEquals("edm task test", report.getTaskDescription());
        assertFalse(report.isReadyToReview());


    }

    @Test
    void checkEmployees() {
        Report report = reportService.getReportByTaskName("eee");
        assertEquals(2, report.getEmployees().size());
        assertEquals("Aidar", report.getEmployees().get(0).getFullName());
        assertNull(report.getEmployees().get(0).getUser());
    }

    @Test
    void checkDocuments() {
        Report report = reportService.getReportByTaskName("eee");
        assertEquals(3, report.getDocuments().size());
        assertEquals("test document3", report.getDocuments().get(2).getName());
        assertNull(report.getDocuments().get(2).getOwner().getPassword());
        assertEquals("Victor", report.getDocuments().get(2).getOwner()
                .getEmployee().getFullName());
        assertEquals(2, report.getDocuments().get(2).getOwner()
                .getEmployee().getId());
        assertNull(report.getDocuments().get(2).getOwner()
                .getEmployee().getUser());
    }
}