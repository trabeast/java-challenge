package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.dtos.EmployeeRequestDTO;
import jp.co.axa.apidemo.dtos.EmployeeResponseDTO;
import jp.co.axa.apidemo.dtos.PostEmployeeRequestDTO;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "spring.jackson.deserialization.fail-on-unknown-properties=true"
})
@AutoConfigureMockMvc
class EmployeeControllerPostIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService<EmployeeResponseDTO, EmployeeRequestDTO> employeeService;

    @ParameterizedTest
    @ValueSource(strings = {"{\"name\":\"John\",\"salary\":10000,\"department\":\"IT\"}"})
    void testPostEmployeesShouldReturn200(String requestBody) throws Exception {
        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                        .andExpect(status().isOk());
        verify(employeeService).saveEmployee(any(PostEmployeeRequestDTO.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"John\",\"salary\":-10000, \"department\": \"IT\"}",
            "{\"name\":\"John\",\"salary\":null, \"department\": \"IT\"}",
            "{\"name\":\"\",\"salary\":10000, \"department\": \"IT\"}",
            "{\"name\":null,\"salary\":10000, \"department\": \"IT\"}",
            "{\"name\":\"John\",\"salary\":10000, \"department\": \"\"}",
            "{\"name\":\"John\",\"salary\":10000, \"department\": null}",
            "{\"name\":\"John\",\"salary\":10000,\"department\":\"IT\",\"foo\":\"bar\"}"
    })
    void testPostEmployeesShouldReturn400(String requestBody) throws Exception {
        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).saveEmployee(any(PostEmployeeRequestDTO.class));
    }
}
