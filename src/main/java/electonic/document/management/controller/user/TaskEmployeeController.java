package electonic.document.management.controller.user;

import electonic.document.management.model.Task;
import electonic.document.management.model.user.Duty;
import electonic.document.management.model.user.Employee;
import electonic.document.management.model.user.TaskEmployee;
import electonic.document.management.service.user.TaskEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("task_employees")
public class TaskEmployeeController {
    private final TaskEmployeeService taskEmployeeService;

    public TaskEmployeeController(TaskEmployeeService taskEmployeeService) {
        this.taskEmployeeService = taskEmployeeService;
    }

    @PostMapping
    // TODO: 14.12.2020 add default duty after registration?
    public ResponseEntity<?> addTaskEmployee(@RequestParam("employee_id") Employee employee,
                                             @RequestParam("task_id") Task task) {
        if (!taskEmployeeService.addTaskEmployee(task, employee)) {
            return ResponseEntity.ok("This employee is already participating in this assignment." +
                    " Do you want to add a duty?");
        }
        return ResponseEntity.ok("Task employee was registered");
    }

    @GetMapping
    public ResponseEntity<?> getAllTaskEmployees() {
        List<TaskEmployee> allTaskEmployees = taskEmployeeService.getAllTaskEmployees();

        return ResponseEntity.ok("");
    }

    @DeleteMapping("{task_employee_id}")
    public ResponseEntity<?> deleteTaskEmployee(@PathVariable("task_employee_id") TaskEmployee employee) {
        taskEmployeeService.deleteTaskEmployee(employee);

        return ResponseEntity.ok("");
    }

    @PostMapping("{task_employee_id}/positions")
    public ResponseEntity<?> setTaskEmployeePosition(@PathVariable("task_employee_id") TaskEmployee employee,
                                                     @RequestParam("duty") Duty duty) {

        if (!taskEmployeeService.setTaskEmployeePosition(employee, duty)) {
            return ResponseEntity.ok("User with such id is not exists!");
        }

        return ResponseEntity.ok("Operation is success!");

    }

    @DeleteMapping("{task_employee_id}/positions/{duty}")
    public ResponseEntity<?> deleteTaskEmployeePosition(@PathVariable("task_employee_id") TaskEmployee employee,
                                                        @PathVariable("duty") Duty duty) {
        if(!taskEmployeeService.deleteTaskEmployeePosition(employee, duty)){
            return ResponseEntity.ok("TaskEmployee not found");
        }
        return ResponseEntity.ok("Operation is success!");
    }
}
