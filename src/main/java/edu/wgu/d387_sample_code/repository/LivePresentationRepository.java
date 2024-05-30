package edu.wgu.d387_sample_code.repository;

import edu.wgu.d387_sample_code.entity.LivePresentation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LivePresentationRepository extends JpaRepository<LivePresentation, Long> {
    List<LivePresentation> findByTitleContainingIgnoreCase(String title);
    List<LivePresentation> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<LivePresentation> findByPresenterContainingIgnoreCase(String presenter);
    List<LivePresentation> findByDateTimeAfter(LocalDateTime dateTime);
    List<LivePresentation> findByDateTimeBefore(LocalDateTime dateTime);
    Optional<LivePresentation> findFirstByDateTimeAfterOrderByDateTimeAsc(LocalDateTime dateTime);
}
