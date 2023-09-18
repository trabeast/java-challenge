package jp.co.axa.apidemo.dtos;

import jp.co.axa.apidemo.dtos.validators.DepartmentConstraint;
import jp.co.axa.apidemo.dtos.validators.NameConstraint;
import jp.co.axa.apidemo.dtos.validators.SalaryConstraint;
import jp.co.axa.apidemo.entities.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostEmployeeRequestDTO implements EmployeeRequestDTO {

    @NameConstraint
    private String name;

    @SalaryConstraint
    private Integer salary;

    @DepartmentConstraint
    private String department;

    /** Convert DTO to entity */
    @Override
    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setId(null);
        employee.setName(this.getName());
        employee.setSalary(this.getSalary());
        employee.setDepartment(this.getDepartment());
        return employee;
    }
}
