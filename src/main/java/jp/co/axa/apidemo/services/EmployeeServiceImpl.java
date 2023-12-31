package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.dtos.*;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService<EmployeeResponseDTO, EmployeeRequestDTO> {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(
            @Autowired
            EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Cacheable("employees")
    @Override
    public List<GetEmployeeResponseDTO> retrieveEmployees() {
        return employeeRepository
                .findAll()
                .stream()
                .map(GetEmployeeResponseDTO::new).collect(Collectors.toList());
    }

    public GetEmployeeResponseDTO getEmployee(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(!employee.isPresent()) {
            throw new NoSuchElementException(String.format("Employee with id %d not found", employeeId));
        }
        return new GetEmployeeResponseDTO(employee.get());
    }

    @CacheEvict(value = "employees", allEntries = true)
    @Override
    public PostEmployeeResponseDTO saveEmployee(EmployeeRequestDTO requestDTO) {
        return new PostEmployeeResponseDTO(employeeRepository.save(requestDTO.toEmployee()));
    }

    @CacheEvict(value = "employees", allEntries = true)
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @CacheEvict(value = "employees", allEntries = true)
    @Override
    public PutEmployeeResponseDTO updateEmployee(EmployeeRequestDTO requestDTO) {
        return new PutEmployeeResponseDTO(employeeRepository.save(requestDTO.toEmployee()));
    }
}
