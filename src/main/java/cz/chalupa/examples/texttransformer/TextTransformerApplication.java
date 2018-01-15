package cz.chalupa.examples.texttransformer;

import cz.chalupa.examples.texttransformer.filters.CorrelationIdFilter;
import cz.chalupa.examples.texttransformer.filters.RequestLoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

@SpringBootApplication
public class TextTransformerApplication {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC)); // Set default timezone to UTC
    }

    @Bean
    public static Filter requestLoggingFilter() {
        Set<String> ignoredPaths = new HashSet<>(2);
        ignoredPaths.add("/info");
        ignoredPaths.add("/health");
        return new RequestLoggingFilter(ignoredPaths);
    }

    @Bean
    public static Filter correlationIdFilter() {
        return new CorrelationIdFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(TextTransformerApplication.class, args);
    }
}
