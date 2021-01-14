package electonic.document.management.utils;

import electonic.document.management.model.Task;
import electonic.document.management.model.document.Document;
import electonic.document.management.model.report.Report;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.TaskEmployee;
import electonic.document.management.repository.projections.DocumentDetailsChooseI;

import java.util.ArrayList;
import java.util.List;


public class ReportUtils {

    public static Report getReportFromTaskWithoutDocuments(Task task) {
        List<Employee> employeeList = new ArrayList<>(task.getTaskEmployees().size());
        for (TaskEmployee taskEmployee : task.getTaskEmployees()) {
            Employee em = taskEmployee.getEmployee();
            Employee newEmpl = new Employee();
            newEmpl.setFullName(em.getFullName());
            newEmpl.setId(em.getId());
            newEmpl.setPhone(em.getPhone());
            employeeList.add(newEmpl);
        }
        Report report = new Report(task.getId(), task.getName(), task.getTaskDescription()
                , task.getCreationDate(), task.getExpiryDate(), task.isReadyToReview(), employeeList
                , task.getDocuments());
        return report;
    }

    public static void setDocuments(Report report, List<DocumentDetailsChooseI> documents) {
        List<Document> documentList=new ArrayList<>(documents.size());
        for(DocumentDetailsChooseI doc:documents){
            Document document = new Document();
            document.setId(doc.getId());
            document.setName(doc.getName());
            document.setFileType(doc.getFileType());
            document.setCreationDate(doc.getCreationDate());
            documentList.add(document);
        }
        report.setDocuments(documentList);
    }
}
