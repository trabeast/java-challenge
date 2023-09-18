package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.dtos.EmployeeRequestDTO;
import jp.co.axa.apidemo.dtos.EmployeeResponseDTO;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "spring.jackson.deserialization.fail-on-unknown-properties=true"
})
@AutoConfigureMockMvc
class EmployeeControllerDeleteIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService<EmployeeResponseDTO, EmployeeRequestDTO> employeeService;

    @Test
    void testDeleteEmployeesShouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/v1/employees/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(employeeService).deleteEmployee(anyLong());
    }

    @Test
    void testDeleteEmployeesShouldReturn400() throws Exception {
        doThrow(EmptyResultDataAccessException.class)
                .when(employeeService).deleteEmployee(anyLong());

        mockMvc.perform(delete("/api/v1/employees/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

