package com.example.demo.invadersdetector.controller;

import static com.example.demo.invadersdetector.testUtils.MultipartFileUtils.getMultipartFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.invadersdetector.InvadersDetectorApplication;
import com.example.demo.invadersdetector.dto.DetectRequest;
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

import java.util.List;

@SpringBootTest(
        classes = InvadersDetectorApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@ActiveProfiles("test")
class DetectorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testDetectInvaderFromFile() throws Exception {

        MockMultipartFile mockMultipartFile = getMultipartFile("radar-data.txt");
        MockMultipartFile invader1 = getMultipartFile("invader1.txt");
        MockMultipartFile invader2 = getMultipartFile("invader2.txt");
        String responseInvader1 = mockMvc.perform(multipart("/invader/invader1")
                        .file(invader1)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Invader createdInvader1 = objectMapper.readValue(responseInvader1, Invader.class);
        String responseInvader2 = mockMvc.perform(multipart("/invader/invader2")
                        .file(invader2)
                        .contentType(MediaType.MULTIPART_FORM_DATA)).andReturn()
                .getResponse()
                .getContentAsString();
        Invader createdInvader2 = objectMapper.readValue(responseInvader2, Invader.class);

        mockMvc.perform(multipart(String.format("/detect/%s,%s/0.1", createdInvader1.getId(), createdInvader2.getId()))
                        .file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }


    @Test
    void testDetectInvaderFromJson() throws Exception {

        List<String> radarData = FileParser.parseAndValidateLines(getMultipartFile("radar-data.txt"));
        MockMultipartFile invader1 = getMultipartFile("invader1.txt");
        MockMultipartFile invader2 = getMultipartFile("invader2.txt");
        String responseInvader1 = mockMvc.perform(multipart("/invader/invader1")
                        .file(invader1)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Invader createdInvader1 = objectMapper.readValue(responseInvader1, Invader.class);
        String responseInvader2 = mockMvc.perform(multipart("/invader/invader2")
                        .file(invader2)
                        .contentType(MediaType.MULTIPART_FORM_DATA)).andReturn()
                .getResponse()
                .getContentAsString();
        Invader createdInvader2 = objectMapper.readValue(responseInvader2, Invader.class);
        DetectRequest detectRequest = new DetectRequest(radarData, List.of(createdInvader2.getId(), createdInvader1.getId()));
        String json = objectMapper.writeValueAsString(detectRequest);
        mockMvc.perform(post("/detect/0.1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}