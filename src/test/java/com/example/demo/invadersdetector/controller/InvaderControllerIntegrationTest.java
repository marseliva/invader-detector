package com.example.demo.invadersdetector.controller;

import static com.example.demo.invadersdetector.testUtils.MultipartFileUtils.getMultipartFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.invadersdetector.InvadersDetectorApplication;
import com.example.demo.invadersdetector.dto.Invader;
import com.example.demo.invadersdetector.util.FileParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@SpringBootTest(
        classes = InvadersDetectorApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@ActiveProfiles("test")
public class InvaderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndGetAndDeleteInvaderJson() throws Exception {

        List<String> invaderPattern = FileParser.parseAndValidateLines(getMultipartFile("invader1.txt"));
        Invader request = Invader.builder().name("TestInvader").pattern(invaderPattern).build();
        String json = objectMapper.writeValueAsString(request);

        String response = mockMvc.perform(post("/invader")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("TestInvader"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Invader created = objectMapper.readValue(response, Invader.class);
        UUID id = created.getId();

        mockMvc.perform(get("/invader/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("TestInvader"));

        mockMvc.perform(get("/invader"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id=='" + id + "')]").exists());

        mockMvc.perform(delete("/invader/" + id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/invader/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateInvaderWithFile() throws Exception {
        mockMvc.perform(multipart("/invader/MyInvader")
                        .file(getMultipartFile("invader1.txt"))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("MyInvader"))
                .andExpect(jsonPath("$.pattern[0]").isNotEmpty());
    }

    @Test
    void testCreateInvaderWithInvalidFileFormat() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "dummy".getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/invader/Invalid")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }


}
