package jp.co.axa.apidemo.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.dtos.EmployeeRequestDTO;
import jp.co.axa.apidemo.dtos.EmployeeResponseDTO;
import jp.co.axa.apidemo.dtos.GetEmployeeResponseDTO;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "spring.jackson.deserialization.fail-on-unknown-properties=true"
})
@AutoConfigureMockMvc
class EmployeeControllerGetIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService<EmployeeResponseDTO, EmployeeRequestDTO> employeeService;

    @ParameterizedTest
    @ValueSource(strings = {
            "[{\"id\":1,\"name\":\"John\",\"salary\":-10000, \"department\": \"IT\"}]",
            "[]"
    })
    void testGetEmployeesShouldReturn200(String employeeServiceReturn) throws Exception {
        ObjectMapper om = new ObjectMapper();
        List<GetEmployeeResponseDTO> employee = om.readValue(employeeServiceReturn,
                new TypeReference<List<GetEmployeeResponseDTO>>() {});

        if(employee.isEmpty()) {
            doReturn(Collections.emptyList()).when(employeeService).retrieveEmployees();
            mockMvc.perform(get("/api/v1/employees/")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.employees").isEmpty());
        } else {
            doReturn(employee).when(employeeService).retrieveEmployees();
            mockMvc.perform(get("/api/v1/employees/")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.employees[0].id").value(1))
                    .andExpect(jsonPath("$.employees[0].name").value("John"))
                    .andExpect(jsonPath("$.employees[0].salary").value(-10000))
                    .andExpect(jsonPath("$.employees[0].department").value("IT"));
        }
    }

    @Test
    void testGetEmployeeByIdShouldReturn200() throws Exception {
        String employeeServiceReturn = "{\"id\":1,\"name\":\"John\",\"salary\":10000, \"department\": \"IT\"}";
        ObjectMapper om = new ObjectMapper();
        GetEmployeeResponseDTO employee = om.readValue(employeeServiceReturn, GetEmployeeResponseDTO.class);
        doReturn(employee).when(employeeService).getEmployee(1L);
        mockMvc.perform(get("/api/v1/employees/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].id").value(1))
                .andExpect(jsonPath("$.employees[0].name").value("John"))
                .andExpect(jsonPath("$.employees[0].salary").value(10000))
                .andExpect(jsonPath("$.employees[0].department").value("IT"));
    }

    @Test
    void testGetEmployeeByIdShouldReturn400() throws Exception {
        when(employeeService.getEmployee(999L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/api/v1/employees/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(employeeService, never()).getEmployee(1L);
    }
}

