package jp.co.axa.apidemo.controllers.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import jp.co.axa.apidemo.dtos.EmployeeResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWithEmployees<T extends EmployeeResponseDTO> extends Response {

    private final List<T> employees;

    public ResponseWithEmployees(String message, List<T> employees) {
        super(message);
        this.employees = employees;
    }
}
