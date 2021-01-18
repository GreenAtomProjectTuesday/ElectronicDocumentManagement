package electonic.document.management.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import electonic.document.management.model.Views;
import electonic.document.management.model.report.Report;
import electonic.document.management.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("reports")
public class ReportController {
    @Resource(name = "objectMapperForReport")
    private final ObjectMapper objectMapper;
    private final ReportService reportService;

    public ReportController(ObjectMapper objectMapper, ReportService reportService) {
        this.objectMapper = objectMapper;
        this.reportService = reportService;
    }
    @GetMapping("{task_name}")
    public ResponseEntity<?> printReport(@PathVariable("task_name") String taskName) throws JsonProcessingException {
        Report report =reportService.getReportByTaskName(taskName);
        return ResponseEntity.ok(objectMapper
                .writerWithView(Views.FullClass.class)
                .writeValueAsString(report));
    }
}
