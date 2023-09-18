package jp.co.axa.apidemo.dtos;

import jp.co.axa.apidemo.entities.Employee;

public interface EmployeeResponseDTO {
    EmployeeResponseDTO fromEmployee(Employee employee);
}
