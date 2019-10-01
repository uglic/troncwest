package ru.uglic.troncwest.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource r = new ResourceBundleMessageSource();
        r.addBasenames("errors");
        r.setDefaultEncoding("utf-8");
        r.setFallbackToSystemLocale(true);
        r.setUseCodeAsDefaultMessage(false);
        return r;
    }
}
