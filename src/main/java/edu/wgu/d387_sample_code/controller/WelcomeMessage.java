package edu.wgu.d387_sample_code.controller;

import org.springframework.core.io.ClassPathResource;

import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class WelcomeMessage implements Runnable {

    private Locale locale;
    private String message;

    public WelcomeMessage(Locale locale) {
        this.locale = locale;
    }

    public String getMessage(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale);

        return resourceBundle.getString("welcome");
    }

    @Override
    public void run() {
        message = getMessage();
        System.out.println(message);
    }
}
// new repo commit test