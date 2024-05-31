package edu.wgu.d387_sample_code;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wgu.d387_sample_code.entity.LivePresentation;
import edu.wgu.d387_sample_code.repository.LivePresentationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LivePresentationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivePresentationRepository livePresentationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        livePresentationRepository.deleteAll();
    }

    @Test
    public void testCreateLivePresentation() throws Exception {
        LivePresentation presentation = new LivePresentation();
        presentation.setTitle("Test Title");
        presentation.setDescription("Test Description");
        presentation.setDateTime(LocalDateTime.now());
        presentation.setPresenter("Test Presenter");

        mockMvc.perform(post("/api/live-presentations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(presentation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"));
    }

    @Test
    public void testSearchLivePresentations() throws Exception {
        LivePresentation presentation = new LivePresentation();
        presentation.setTitle("Test Title");
        presentation.setDescription("Test Description");
        presentation.setDateTime(LocalDateTime.now());
        presentation.setPresenter("Test Presenter");
        livePresentationRepository.save(presentation);

        mockMvc.perform(get("/api/live-presentations/search")
                        .param("query", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].title").value("Test Title"));
    }
}
