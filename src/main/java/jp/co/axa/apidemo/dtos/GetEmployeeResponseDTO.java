package jp.co.axa.apidemo.dtos;

import jp.co.axa.apidemo.entities.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@NoArgsConstructor
@Getter
@Setter
public class GetEmployeeResponseDTO implements EmployeeResponseDTO {
    private Long id;
    private String name;
    private Integer salary;
    private String department;

    public GetEmployeeResponseDTO(@NonNull Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.salary = employee.getSalary();
        this.department = employee.getDepartment();
    }
}
