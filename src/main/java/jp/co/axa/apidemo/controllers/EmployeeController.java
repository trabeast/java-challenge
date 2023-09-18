package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.controllers.response.ResponseWithEmployees;
import jp.co.axa.apidemo.dtos.*;
import jp.co.axa.apidemo.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController implements ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService<EmployeeResponseDTO, EmployeeRequestDTO> employeeService;

    public EmployeeController(
            @Autowired()
            EmployeeService<EmployeeResponseDTO, EmployeeRequestDTO> employeeService
    ) {
        this.employeeService = employeeService;
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/employees")
    public ResponseEntity<ResponseWithEmployees<GetEmployeeResponseDTO>> getEmployees() {
        return ResponseEntity.ok (new ResponseWithEmployees<>(
                "Employees Retrieved Successfully",
                (List<GetEmployeeResponseDTO>) employeeService.retrieveEmployees())
        );
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<ResponseWithEmployees<GetEmployeeResponseDTO>> getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return ResponseEntity.ok(new ResponseWithEmployees<>(
                "Employee Retrieved Successfully",
                Collections.singletonList((GetEmployeeResponseDTO) employeeService.getEmployee(employeeId))));
    }

    @PostMapping("/employees")
    public ResponseEntity<ResponseWithEmployees<PostEmployeeResponseDTO>> saveEmployee(
            @Valid
            @RequestBody
            PostEmployeeRequestDTO requestDTO) {
        LOGGER.info("Employee Saved Successfully");
        return ResponseEntity.ok(new ResponseWithEmployees<>(
                "Employee Saved Successfully",
                Collections.singletonList((PostEmployeeResponseDTO) employeeService.saveEmployee(requestDTO))));
    }

    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        LOGGER.info("Employee Deleted Successfully");
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<ResponseWithEmployees<PutEmployeeResponseDTO>> updateEmployee(
            @Valid
            @RequestBody
            PutEmployeeRequestDTO requestDTO,
            @PathVariable(name = "employeeId")
            Long id) {

        try {
            employeeService.getEmployee(id);
        } finally {
            requestDTO.setId(id);
            LOGGER.info("Employee Updated Successfully");
        }

        return ResponseEntity.ok(new ResponseWithEmployees<>("Employee Updated Successfully",
                Collections.singletonList((PutEmployeeResponseDTO) employeeService.updateEmployee(requestDTO))));
    }
}
