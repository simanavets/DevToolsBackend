package com.orderservice.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    private final String path = "/api/v1/orders";

    private final String order = """
            {"id": 1,"clientId": 1,"description": "some description","status": "OPENED"}
            """;

    @Test
    void findAllReturnsOK() throws Exception {
        mockMvc.perform(get(path))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().json("[" + order + "]")
                );
    }

    @Test
    void findByIdReturnsOk() throws Exception {
        mockMvc.perform(get(path + "/1"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().json(order)
                );
    }

    @Test
    void findByIdReturnsNotFound() throws Exception {
        var expectedBody = "No order with id = 10";
        mockMvc.perform(get(path + "/10"))
                .andExpectAll(
                        status().isNotFound(),
                        content().string(expectedBody)
                );
    }

    @Test
    void findByIdReturnsBadRequest() throws Exception {
        var expectedBody = "Id should be more than 1";
        var mvcResult = mockMvc.perform(get(path + "/0"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .contains(expectedBody);
    }

    @Test
    void updateStatusReturnsOk() throws Exception {
        var requestBody = """
                {"status":"DELIVERED"}
                """;
        var expectedBody = """
                {"id": 1,"clientId": 1,"description": "some description","status": "DELIVERED"}
                """;

        mockMvc.perform(get(path + "/1"))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().json(order)
                );

        mockMvc.perform(put(path + "/1").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        content().json(expectedBody)
                );
    }

    @Test
    void deleteByIdReturnsNoContent() throws Exception {
        mockMvc.perform(delete(path + "/1"))
                .andExpect(status().isNoContent());
    }
}