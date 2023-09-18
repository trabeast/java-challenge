package jp.co.axa.apidemo.dtos;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.lang.NonNull;

/**
 * Employee request DTOs. Each api will have its own implementation.
 */
public interface EmployeeRequestDTO {
    /**
     * Convert to Entity
     */
    @NonNull
    Employee toEmployee();
}
