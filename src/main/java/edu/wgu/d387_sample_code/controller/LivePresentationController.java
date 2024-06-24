package edu.wgu.d387_sample_code.controller;

import edu.wgu.d387_sample_code.entity.LivePresentation;
import edu.wgu.d387_sample_code.service.LivePresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://d424-software-engineering-capstone-5rih.onrender.com")
@RequestMapping("/api/live-presentations")
public class LivePresentationController {

    @Autowired
    private LivePresentationService service;

    @PostMapping
    public ResponseEntity<LivePresentation> createLivePresentation(@RequestBody LivePresentation livePresentation) {
        LivePresentation createdLivePresentation = service.createLivePresentation(livePresentation);
        return ResponseEntity.ok(createdLivePresentation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivePresentation> getLivePresentationById(@PathVariable Long id) {
        Optional<LivePresentation> livePresentation = service.getLivePresentationById(id);
        return livePresentation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivePresentation> updateLivePresentation(@PathVariable Long id, @RequestBody LivePresentation updatedLivePresentation) {
        LivePresentation updated = service.updateLivePresentation(id, updatedLivePresentation);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivePresentation(@PathVariable Long id) {
        service.deleteLivePresentation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchLivePresentations(@RequestParam String query) {
        List<LivePresentation> results = service.searchLivePresentations(query);
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("results", results);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/between-dates")
    public ResponseEntity<List<LivePresentation>> getPresentationsBetweenDates(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {
        return ResponseEntity.ok(service.getPresentationsBetweenDates(start, end));
    }

    @GetMapping("/by-presenter")
    public ResponseEntity<List<LivePresentation>> searchByPresenter(@RequestParam String presenter) {
        return ResponseEntity.ok(service.searchByPresenter(presenter));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<LivePresentation>> getUpcomingPresentations(@RequestParam LocalDateTime dateTime) {
        return ResponseEntity.ok(service.getUpcomingPresentations(dateTime));
    }

    @GetMapping("/past")
    public ResponseEntity<List<LivePresentation>> getPastPresentations(@RequestParam LocalDateTime dateTime) {
        return ResponseEntity.ok(service.getPastPresentations(dateTime));
    }

    @GetMapping
    public ResponseEntity<List<LivePresentation>> getAllPresentations() {
        return ResponseEntity.ok(service.findAll());
    }
}
