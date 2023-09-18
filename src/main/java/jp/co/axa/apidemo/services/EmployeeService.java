package jp.co.axa.apidemo.services;

import org.springframework.lang.NonNull;

import java.util.List;

public interface EmployeeService<T, R> {

    @NonNull List<? extends T> retrieveEmployees();

    @NonNull T getEmployee(@NonNull Long employeeId);

    @NonNull T saveEmployee(@NonNull R requestDTO);

    void deleteEmployee(@NonNull Long employeeId);

    @NonNull T updateEmployee(@NonNull R requestDTO);
}
