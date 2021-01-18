package electonic.document.management.service;

import electonic.document.management.model.Task;
import electonic.document.management.model.document.Document;
import electonic.document.management.model.report.Report;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.User;
import electonic.document.management.repository.DocumentRepository;
import electonic.document.management.repository.ReportRepository;
import electonic.document.management.repository.projections.DocumentProjectionI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static electonic.document.management.utils.ReportUtils.getReportFromTaskWithoutDocuments;
import static electonic.document.management.utils.ReportUtils.setDocuments;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final DocumentRepository documentRepository;

    public ReportService(ReportRepository reportRepository, DocumentRepository documentRepository) {
        this.reportRepository = reportRepository;
        this.documentRepository = documentRepository;
    }

    @Transactional
    public Report getReportByTaskName(String taskName) {
        Task task = reportRepository.getTaskByName(taskName);
        Report report = getReportFromTaskWithoutDocuments(task);
        List<DocumentProjectionI> documents = documentRepository
                .getAllDocumentsByTaskName(taskName);
        setDocuments(report, documents);
        setDocumentOwners(report);
        return report;
    }

    private void setDocumentOwners(Report report) {
        for (Document doc : report.getDocuments()) {
            String name = doc.getName();
            for (Employee employee : report.getEmployees()) {
                if (documentRepository.getUserIdByName(name).equals(employee.getId())) {
                    User newUser = new User();
                    newUser.setEmployee(employee);
                    doc.setOwner(newUser);
                    break;
                }
            }
        }
    }
}
