package com.pranav.microservices.chatapp_backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.LocalDateTime;

public class JacksonTest {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())  // Enable Java 8 Date/Time support
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);  // Serialize as ISO-8601 string

        // Create a sample LocalDateTime object
        LocalDateTime now = LocalDateTime.now();

        // Serialize LocalDateTime to JSON
        String json = mapper.writeValueAsString(now);
        System.out.println("Serialized JSON: " + json);

        // Deserialize JSON back to LocalDateTime
        LocalDateTime deserialized = mapper.readValue(json, LocalDateTime.class);
        System.out.println("Deserialized LocalDateTime: " + deserialized);
    }
}
