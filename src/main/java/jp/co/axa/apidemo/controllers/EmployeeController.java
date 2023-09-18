package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.dtos.EmployeeRequestDTO;
import jp.co.axa.apidemo.dtos.EmployeeResponseDTO;
import jp.co.axa.apidemo.dtos.PostEmployeeRequestDTO;
import jp.co.axa.apidemo.dtos.PostEmployeeResponseDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService<EmployeeResponseDTO, EmployeeRequestDTO> employeeService;

    public EmployeeController(
            @Autowired()
            EmployeeService<EmployeeResponseDTO, EmployeeRequestDTO> employeeService
    ) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return employees;
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PostMapping("/employees")
    public void saveEmployee(@RequestBody PostEmployeeRequestDTO requestDTO){
        PostEmployeeResponseDTO responseDTO = (PostEmployeeResponseDTO) employeeService.saveEmployee(requestDTO);
        LOGGER.info("Employee Saved Successfully");
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        employeeService.deleteEmployee(employeeId);
        LOGGER.info("Employee Deleted Successfully");
    }

    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee,
                               @PathVariable(name="employeeId")Long employeeId){
        Employee emp = employeeService.getEmployee(employeeId);
        if(emp != null){
            employeeService.updateEmployee(employee);
        }

    }

}
