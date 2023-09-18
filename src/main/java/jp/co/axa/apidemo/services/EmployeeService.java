package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.lang.NonNull;

import java.util.List;

public interface EmployeeService<T, R> {

    @NonNull List<Employee> retrieveEmployees();

    @NonNull Employee getEmployee(@NonNull Long employeeId);

    @NonNull T saveEmployee(@NonNull R requestDTO);

    void deleteEmployee(@NonNull Long employeeId);

    @NonNull T updateEmployee(@NonNull R requestDTO);
}
