package edu.wgu.d387_sample_code.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@CrossOrigin(origins = "https://d424-software-engineering-capstone-5rih.onrender.com")
@RequestMapping("/api")
public class WelcomeMessageController {
    @GetMapping("/welcome/en")
    public ResponseEntity<String> getWelcomeMessageEnglish() {
        Locale locale = Locale.US; // Set locale to US for English
        WelcomeMessage welcomeMessage = new WelcomeMessage(locale);
        String message = welcomeMessage.getMessage();
        return new ResponseEntity<String>(welcomeMessage.getMessage(), HttpStatus.OK);
    }

    @GetMapping("/welcome/fr")
    public ResponseEntity<String> getWelcomeMessageFrench() {
        Locale locale = Locale.FRANCE; // Set locale to FRANCE for French
        WelcomeMessage welcomeMessage = new WelcomeMessage(locale);
        String message = welcomeMessage.getMessage();
        return new ResponseEntity<String>(welcomeMessage.getMessage(), HttpStatus.OK);
    }
}
