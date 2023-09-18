package jp.co.axa.apidemo.dtos;

import jp.co.axa.apidemo.entities.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostEmployeeResponseDTO implements EmployeeResponseDTO {
    private Long id;
    private String name;
    private Integer salary;
    private String department;

    public PostEmployeeResponseDTO() {}

    public PostEmployeeResponseDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.salary = employee.getSalary();
        this.department = employee.getDepartment();
    }

    /* Convert to Entity */
    @Override
    public PostEmployeeResponseDTO fromEmployee(Employee employee) {
        PostEmployeeResponseDTO dto = new PostEmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSalary(employee.getSalary());
        dto.setDepartment(employee.getDepartment());
        return dto;
    }
}
