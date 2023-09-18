package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dtos.EmployeeRequestDTO;
import jp.co.axa.apidemo.dtos.EmployeeResponseDTO;
import jp.co.axa.apidemo.dtos.PostEmployeeResponseDTO;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService<EmployeeResponseDTO, EmployeeRequestDTO> {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(
            @Autowired
            EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        return optEmp.get();
    }

    public PostEmployeeResponseDTO saveEmployee(EmployeeRequestDTO requestDTO) {
        return new PostEmployeeResponseDTO(employeeRepository.save(requestDTO.toEmployee()));
    }

    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}
