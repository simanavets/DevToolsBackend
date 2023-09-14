package com.devtoolsbackend.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    private final String path = "/api/v1/orders";

    private final String order = """
            {"id":1,"description":"some description","status":"OPENED","client":{"id":1,"email":"sidorov@gmail.com"}}
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
        var expectedBody = "No order with id = 4";
        mockMvc.perform(get(path + "/4"))
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
    void createOrderWithNoClientReturnsCreated() throws Exception {
        var requestBody = """
                 {"description":"second order","status":"OPENED"}
                """;
        var expectedBody = """
                {"id":2,"description":"second order","status":"OPENED","client":{"id":2,"email":"default client"}}
                                """;

        mockMvc.perform(post(path).contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpectAll(
                        status().isCreated(),
                        content().json(expectedBody)
                );
    }

    @Test
    void updateStatusReturnsOk() throws Exception {
        var requestBody = """
                {"status":"DELIVERED"}
                """;
        var expectedBody = """
                {"id":1,"description":"some description","status":"DELIVERED","client":{"id":1,"email":"sidorov@gmail.com"}}
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