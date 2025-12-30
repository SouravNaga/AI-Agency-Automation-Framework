package org.aiagency.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Map;

public class PageXPathStore {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String get(String page, String key) {
        try {
            String path = "xpaths/" + page.toLowerCase() + ".json";

            InputStream is = PageXPathStore.class
                    .getClassLoader()
                    .getResourceAsStream(path);

            if (is == null) {
                throw new RuntimeException("XPath file not found: " + path);
            }

            Map<String, String> xpaths =
                    mapper.readValue(is, new TypeReference<>() {});

            if (!xpaths.containsKey(key)) {
                throw new RuntimeException(
                        "XPath key '" + key + "' not found in page: " + page);
            }

            return xpaths.get(key);

        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve XPath", e);
        }
    }
}
