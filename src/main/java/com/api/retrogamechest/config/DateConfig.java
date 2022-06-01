package com.api.retrogamechest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.datetime.DateFormatter;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class DateConfig {

    public static final String DATE_FORMAT_SERIALIZER = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT_SERIALIZER = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final LocalDateSerializer LOCAL_DATE_SERIALIZER = new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT_SERIALIZER));
    public static final LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT_SERIALIZER));

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LOCAL_DATE_SERIALIZER);
        javaTimeModule.addSerializer(LOCAL_DATETIME_SERIALIZER);
        return new ObjectMapper().registerModule(javaTimeModule);
    }
}
