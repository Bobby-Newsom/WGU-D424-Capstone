package edu.wgu.d387_sample_code;

import edu.wgu.d387_sample_code.entity.LivePresentation;
import edu.wgu.d387_sample_code.repository.LivePresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private LivePresentationRepository livePresentationRepository;

    @Override
    public void run(String... args) throws Exception {
        loadLivePresentations();
    }

    private void loadLivePresentations() {
        // Clear existing data
        livePresentationRepository.deleteAll();

        // Add test data
        LivePresentation presentation1 = new LivePresentation();
        presentation1.setTitle("Future Presentation 1");
        presentation1.setDescription("Description for Future Presentation 1");
        presentation1.setDateTime(LocalDateTime.now().plusDays(10));
        presentation1.setPresenter("Future Presenter 1");

        LivePresentation presentation2 = new LivePresentation();
        presentation2.setTitle("Future Presentation 2");
        presentation2.setDescription("Description for Future Presentation 2");
        presentation2.setDateTime(LocalDateTime.now().plusDays(20));
        presentation2.setPresenter("Future Presenter 2");

        LivePresentation presentation3 = new LivePresentation();
        presentation3.setTitle("Past Presentation 1");
        presentation3.setDescription("Description for Past Presentation 1");
        presentation3.setDateTime(LocalDateTime.now().minusDays(10));
        presentation3.setPresenter("Past Presenter 1");

        LivePresentation presentation4 = new LivePresentation();
        presentation4.setTitle("Past Presentation 2");
        presentation4.setDescription("Description for Past Presentation 2");
        presentation4.setDateTime(LocalDateTime.now().minusDays(20));
        presentation4.setPresenter("Past Presenter 2");

        LivePresentation presentation5 = new LivePresentation();
        presentation5.setTitle("Presentation with Same Speaker");
        presentation5.setDescription("Description for Presentation with Same Speaker");
        presentation5.setDateTime(LocalDateTime.now().plusDays(5));
        presentation5.setPresenter("Shared Presenter");

        LivePresentation presentation6 = new LivePresentation();
        presentation6.setTitle("Another Presentation with Same Speaker");
        presentation6.setDescription("Description for Another Presentation with Same Speaker");
        presentation6.setDateTime(LocalDateTime.now().minusDays(5));
        presentation6.setPresenter("Shared Presenter");

        livePresentationRepository.saveAll(Arrays.asList(presentation1, presentation2, presentation3, presentation4, presentation5, presentation6));
    }
}
