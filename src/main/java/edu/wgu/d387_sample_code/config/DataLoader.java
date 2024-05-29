package edu.wgu.d387_sample_code.config;

import edu.wgu.d387_sample_code.entity.LivePresentation;
import edu.wgu.d387_sample_code.repository.LivePresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private LivePresentationRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            // Upcoming events
            LivePresentation upcomingPresentation1 = new LivePresentation();
            upcomingPresentation1.setTitle("Future Presentation 1");
            upcomingPresentation1.setDescription("Description of Future Presentation 1");
            upcomingPresentation1.setDateTime(LocalDateTime.now().plusDays(5));
            upcomingPresentation1.setPresenter("Future Presenter 1");
            repository.save(upcomingPresentation1);

            LivePresentation upcomingPresentation2 = new LivePresentation();
            upcomingPresentation2.setTitle("Future Presentation 2");
            upcomingPresentation2.setDescription("Description of Future Presentation 2");
            upcomingPresentation2.setDateTime(LocalDateTime.now().plusDays(10));
            upcomingPresentation2.setPresenter("Future Presenter 2");
            repository.save(upcomingPresentation2);

            // Past events
            LivePresentation pastPresentation1 = new LivePresentation();
            pastPresentation1.setTitle("Past Presentation 1");
            pastPresentation1.setDescription("Description of Past Presentation 1");
            pastPresentation1.setDateTime(LocalDateTime.now().minusDays(5));
            pastPresentation1.setPresenter("Past Presenter 1");
            repository.save(pastPresentation1);

            LivePresentation pastPresentation2 = new LivePresentation();
            pastPresentation2.setTitle("Past Presentation 2");
            pastPresentation2.setDescription("Description of Past Presentation 2");
            pastPresentation2.setDateTime(LocalDateTime.now().minusDays(10));
            pastPresentation2.setPresenter("Past Presenter 2");
            repository.save(pastPresentation2);

            // Events with the same speaker
            LivePresentation sameSpeakerPresentation1 = new LivePresentation();
            sameSpeakerPresentation1.setTitle("Presentation with Same Speaker 1");
            sameSpeakerPresentation1.setDescription("Description of Presentation with Same Speaker 1");
            sameSpeakerPresentation1.setDateTime(LocalDateTime.now().plusDays(3));
            sameSpeakerPresentation1.setPresenter("Same Speaker");
            repository.save(sameSpeakerPresentation1);

            LivePresentation sameSpeakerPresentation2 = new LivePresentation();
            sameSpeakerPresentation2.setTitle("Presentation with Same Speaker 2");
            sameSpeakerPresentation2.setDescription("Description of Presentation with Same Speaker 2");
            sameSpeakerPresentation2.setDateTime(LocalDateTime.now().minusDays(3));
            sameSpeakerPresentation2.setPresenter("Same Speaker");
            repository.save(sameSpeakerPresentation2);
        }
    }
}
