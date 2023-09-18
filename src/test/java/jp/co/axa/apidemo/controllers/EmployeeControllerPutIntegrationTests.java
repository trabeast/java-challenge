package jp.co.axa.apidemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.axa.apidemo.dtos.EmployeeRequestDTO;
import jp.co.axa.apidemo.dtos.EmployeeResponseDTO;
import jp.co.axa.apidemo.dtos.PutEmployeeRequestDTO;
import jp.co.axa.apidemo.dtos.PutEmployeeResponseDTO;
import jp.co.axa.apidemo.services.EmployeeService;
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

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "spring.jackson.deserialization.fail-on-unknown-properties=true"
})
@AutoConfigureMockMvc
class EmployeeControllerPutIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService<EmployeeResponseDTO, EmployeeRequestDTO> employeeService;

    @ParameterizedTest
    @ValueSource(strings = {"{\"name\":\"John\",\"salary\":10000,\"department\":\"IT\"}"})
    void testPutEmployeesShouldReturn200(String requestBody) throws Exception {
        ObjectMapper om = new ObjectMapper();
        PutEmployeeResponseDTO responseDTO = om.readValue(requestBody, PutEmployeeResponseDTO.class);
        doReturn(responseDTO).when(employeeService).updateEmployee(any(EmployeeRequestDTO.class));
        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employees[0].name").value("John"))
                .andExpect(jsonPath("$.employees[0].salary").value(10000))
                .andExpect(jsonPath("$.employees[0].department").value("IT"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"John\",\"salary\":-10000,\"department\":\"IT\"}",
            "{\"name\":\"John\",\"salary\":null,\"department\":\"IT\"}",
            "{\"name\":\"John\",\"salary\":10000,\"department\":null}",
            "{\"name\":\"John\",\"salary\":10000,\"department\":\"\"}",
            "{\"name\":\"John\",\"salary\":10000,\"department\":\"IT\",\"unknown\":\"unknown\"}",
            "{\"name\":null,\"salary\":10000,\"department\":\"IT\"}",
            "{\"name\":\"\",\"salary\":10000,\"department\":\"IT\"}",
    })
    void testPutEmployeesShouldReturn400(String requestBody) throws Exception {
        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
        verify(employeeService, never()).updateEmployee(any(PutEmployeeRequestDTO.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"{\"name\":\"John\",\"salary\":10000,\"department\":\"IT\"}"})
    void testPutEmployeesShouldReturn400WhenIdNotExisting(String requestBody) throws Exception {
        when(employeeService.updateEmployee(any(PutEmployeeRequestDTO.class)))
                .thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/api/v1/employees/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
        verify(employeeService).updateEmployee(any(PutEmployeeRequestDTO.class));
    }
}

