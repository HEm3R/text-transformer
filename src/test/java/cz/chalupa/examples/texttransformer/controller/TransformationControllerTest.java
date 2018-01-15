package cz.chalupa.examples.texttransformer.controller;

import cz.chalupa.examples.texttransformer.SecurityConfiguration;
import cz.chalupa.examples.texttransformer.TextTransformerApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.Base64Utils;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Import({TextTransformerApplication.class, SecurityConfiguration.class})
class TransformationControllerTest {

    private static final String URL = "/api/transform";

    @Autowired MockMvc mockMvc;

    @Test
    public void shouldReturnTransformedValue() throws Exception {
        mockMvc.perform(authorized(postJson("{\"value\":\"test\"}")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transformedValue", is("tSet")));
    }

    @Test
    public void shouldReturnUnauthorizedWhenAuthInfoMissing() throws Exception {
        mockMvc.perform(post(URL)).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorizedWhenAuthInfoInvalid() throws Exception {
        mockMvc.perform(withAuth(post(URL), "root:badPassword")).andExpect(status().isUnauthorized());
    }

    @Test
    public void missingValueShouldBeBadRequest() throws Exception {
        mockMvc.perform(authorized(postJson("{}"))).andExpect(status().isBadRequest());
    }

    @Test
    public void nullValueShouldBeBadRequest() throws Exception {
        mockMvc.perform(authorized(postJson("{\"value\":null}"))).andExpect(status().isBadRequest());
    }

    @Test
    public void xmlContentTypeShouldNotBeSupported() throws Exception {
        mockMvc.perform(authorized(post(URL).content("<x></x>").contentType(MediaType.APPLICATION_XML))).andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void getShouldNotBeAllowed() throws Exception {
        mockMvc.perform(authorized(get(URL))).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deleteShouldNotBeAllowed() throws Exception {
        mockMvc.perform(authorized(delete(URL))).andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void putShouldNotBeAllowed() throws Exception {
        mockMvc.perform(authorized(put(URL))).andExpect(status().isMethodNotAllowed());
    }

    private static MockHttpServletRequestBuilder authorized(MockHttpServletRequestBuilder builder) {
        return withAuth(builder, "root:root");
    }

    private static MockHttpServletRequestBuilder withAuth(MockHttpServletRequestBuilder builder, String auth) {
        return builder.header(HttpHeaders.AUTHORIZATION,"Basic " + Base64Utils.encodeToString(auth.getBytes()));
    }

    private static MockHttpServletRequestBuilder postJson(String payload) {
        return post(URL).content(payload).contentType(MediaType.APPLICATION_JSON);
    }
}
