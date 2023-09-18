package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.axa.apidemo.controllers.response.Response;
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

@Api(tags = {"Employee"}, value = "v1")
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees Retrieved Successfully")
    })
    @SuppressWarnings("unchecked")
    @GetMapping("/employees")
    public ResponseEntity<ResponseWithEmployees<GetEmployeeResponseDTO>> getEmployees() {
        return ResponseEntity.ok (new ResponseWithEmployees<>(
                "Employees Retrieved Successfully",
                (List<GetEmployeeResponseDTO>) employeeService.retrieveEmployees())
        );
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee Retrieved Successfully"),
            @ApiResponse(code = 400, message = "Employee Not Found")
    })
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<ResponseWithEmployees<GetEmployeeResponseDTO>> getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return ResponseEntity.ok(new ResponseWithEmployees<>(
                "Employee Retrieved Successfully",
                Collections.singletonList((GetEmployeeResponseDTO) employeeService.getEmployee(employeeId))));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee Saved Successfully"),
            @ApiResponse(code = 400, message = "Invalid request fields")
    })
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee Deleted Successfully"),
            @ApiResponse(code = 400, message = "Employee Not Found")
    })
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<Response> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        LOGGER.info("Employee Deleted Successfully");
        return ResponseEntity.ok(new Response("Employee Deleted Successfully"));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee Updated Successfully"),
            @ApiResponse(code = 400, message = "Employee Not Found")
    })
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
