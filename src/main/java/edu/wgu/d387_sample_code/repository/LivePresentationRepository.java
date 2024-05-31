package edu.wgu.d387_sample_code.repository;

import edu.wgu.d387_sample_code.entity.LivePresentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LivePresentationRepository extends JpaRepository<LivePresentation, Long> {

    @Query("SELECT lp FROM LivePresentation lp WHERE " +
            "UPPER(lp.title) LIKE UPPER(CONCAT('%', :query, '%')) OR " +
            "UPPER(lp.description) LIKE UPPER(CONCAT('%', :query, '%')) OR " +
            "UPPER(lp.presenter) LIKE UPPER(CONCAT('%', :query, '%')) OR " +
            "CAST(lp.id AS string) LIKE CONCAT('%', :query, '%') OR " +
            "UPPER(CAST(lp.dateTime AS string)) LIKE UPPER(CONCAT('%', :query, '%'))")
    List<LivePresentation> searchLivePresentations(@Param("query") String query);

    List<LivePresentation> findByTitleContainingIgnoreCase(String title);
    List<LivePresentation> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<LivePresentation> findByPresenterContainingIgnoreCase(String presenter);
    List<LivePresentation> findByDateTimeAfter(LocalDateTime dateTime);
    List<LivePresentation> findByDateTimeBefore(LocalDateTime dateTime);
    Optional<LivePresentation> findFirstByDateTimeAfterOrderByDateTimeAsc(LocalDateTime dateTime);
}
