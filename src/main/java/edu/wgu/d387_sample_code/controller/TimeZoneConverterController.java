package edu.wgu.d387_sample_code.controller;

import edu.wgu.d387_sample_code.entity.LivePresentation;
import edu.wgu.d387_sample_code.service.LivePresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class TimeZoneConverterController {

    @Autowired
    private LivePresentationService livePresentationService;

    @GetMapping("/presentation")
    public ResponseEntity<String> getPresentationTimes() {
        Optional<LivePresentation> nextPresentation = livePresentationService.getNextLivePresentation();

        if (nextPresentation.isPresent()) {
            LivePresentation presentation = nextPresentation.get();
            ZonedDateTime presentationTime = presentation.getDateTime().atZone(ZoneId.systemDefault());
            String formattedTime = TimeZoneConverter.getTime(presentationTime);
            String presentationAnnouncement = "Next live presentation: " + presentation.getTitle() +
                    " by " + presentation.getPresenter() + " at " + formattedTime;
            return new ResponseEntity<>(presentationAnnouncement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No upcoming presentations.", HttpStatus.OK);
        }
    }
}
