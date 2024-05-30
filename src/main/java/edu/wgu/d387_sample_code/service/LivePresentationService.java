package edu.wgu.d387_sample_code.service;

import edu.wgu.d387_sample_code.entity.LivePresentation;
import edu.wgu.d387_sample_code.repository.LivePresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LivePresentationService {

    @Autowired
    private LivePresentationRepository repository;
    @Autowired
    private LivePresentationRepository livePresentationRepository;

    public LivePresentation createLivePresentation(LivePresentation livePresentation) {
        return repository.save(livePresentation);
    }

    public Optional<LivePresentation> getLivePresentationById(Long id) {
        return repository.findById(id);
    }

    public Optional<LivePresentation> getNextLivePresentation() {
        return livePresentationRepository.findFirstByDateTimeAfterOrderByDateTimeAsc(LocalDateTime.now());
    }

    public LivePresentation updateLivePresentation(Long id, LivePresentation updatedLivePresentation) {
        return repository.findById(id)
                .map(presentation -> {
                    presentation.setTitle(updatedLivePresentation.getTitle());
                    presentation.setDescription(updatedLivePresentation.getDescription());
                    presentation.setDateTime(updatedLivePresentation.getDateTime());
                    presentation.setPresenter(updatedLivePresentation.getPresenter());
                    return repository.save(presentation);
                })
                .orElseGet(() -> {
                    updatedLivePresentation.setId(id);
                    return repository.save(updatedLivePresentation);
                });
    }

    public void deleteLivePresentation(Long id) {
        repository.deleteById(id);
    }

    public List<LivePresentation> searchByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public List<LivePresentation> getPresentationsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return repository.findByDateTimeBetween(start, end);
    }

    public List<LivePresentation> searchByPresenter(String presenter) {
        return repository.findByPresenterContainingIgnoreCase(presenter);
    }

    public List<LivePresentation> getUpcomingPresentations(LocalDateTime dateTime) {
        return repository.findByDateTimeAfter(dateTime);
    }

    public List<LivePresentation> getPastPresentations(LocalDateTime dateTime) {
        return repository.findByDateTimeBefore(dateTime);
    }

    public List<LivePresentation> findAll() {
        return repository.findAll();
    }
}
