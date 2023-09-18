package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;

import java.util.List;

public interface EmployeeService<T, R> {

    List<Employee> retrieveEmployees();

    Employee getEmployee(Long employeeId);

    T saveEmployee(R employee);

    void deleteEmployee(Long employeeId);

    void updateEmployee(Employee employee);
}
