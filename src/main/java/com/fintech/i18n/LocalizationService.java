package com.fintech.i18n;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class LocalizationService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Map<String, String>> cache = new ConcurrentHashMap<>();

    public String getMessage(String language, String key, String defaultMessage) {
        String lang = (language == null ? "EN" : language).toUpperCase();
        Map<String, String> bundle = cache.computeIfAbsent(lang, this::loadBundle);
        if (bundle == null) return defaultMessage;
        return bundle.getOrDefault(key, defaultMessage);
    }

    private Map<String, String> loadBundle(String lang) {
        String path = "i18n/" + ("JP".equals(lang) ? "jp" : "en") + ".json";
        try (InputStream is = new ClassPathResource(path).getInputStream()) {
            return objectMapper.readValue(is, new TypeReference<Map<String, String>>() {});
        } catch (IOException e) {
            log.error("Failed to load localization bundle: {}", path, e);
            return null;
        }
    }
}
