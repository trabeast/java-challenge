package jp.co.axa.apidemo.dtos;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.lang.NonNull;

public interface EmployeeRequestDTO {
    /**
     * Convert to Entity
     */
    @NonNull
    Employee toEmployee();
}
