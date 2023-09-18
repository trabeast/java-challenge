package jp.co.axa.apidemo.dtos;

import jp.co.axa.apidemo.dtos.validators.DepartmentConstraint;
import jp.co.axa.apidemo.dtos.validators.NameConstraint;
import jp.co.axa.apidemo.dtos.validators.SalaryConstraint;
import jp.co.axa.apidemo.entities.Employee;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutEmployeeRequestDTO implements EmployeeRequestDTO {
    private Long id;

    @NameConstraint
    private String name;

    @SalaryConstraint
    private Integer salary;

    @DepartmentConstraint
    private String department;

    @Override
    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setId(this.id);
        employee.setName(this.name);
        employee.setSalary(this.salary);
        employee.setDepartment(this.department);
        return employee;
    }
}
