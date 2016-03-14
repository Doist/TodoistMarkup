package com.todoist.markup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

class EmojiParser {
    private static final String REGEXP_STANDARD_EMOJI = "(:[a-zA-Z\\p{L}0-9_’\\-\\.\\&]+:)";
    private static final String EMOJI_STANDARD = "emojis_standard.json";
    private static final String EMOJI_TODOIST = "emojis_todoist.json";

    private static Map<String, String> sEmojiMap = new HashMap<>();
    private static Pattern sEmojiPattern;

    static Pattern getEmojiPattern() {
        if (sEmojiPattern == null) {
            try {
                init();
            } catch (IOException e) {
                // Ignore. Not perfect but very unlikely.
            }
        }

        return sEmojiPattern;
    }

    static String getEmoji(String key) {
        return sEmojiMap.get(key);
    }

    static synchronized void init() throws IOException {
        Map<String, String> todoistShortcuts = new HashMap<>();
        if (sEmojiMap.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();

            InputStream standardInputStream = null;
            InputStream todoistInputStream = null;
            ClassLoader classLoader = EmojiParser.class.getClassLoader();

            try {
                standardInputStream = classLoader.getResourceAsStream(EMOJI_STANDARD);
                sEmojiMap = mapper.readValue(standardInputStream, new TypeReference<HashMap<String, String>>() {
                });

                todoistInputStream = classLoader.getResourceAsStream(EMOJI_TODOIST);
                todoistShortcuts = mapper.readValue(todoistInputStream, new TypeReference<HashMap<String, String>>() {
                });

                sEmojiMap.putAll(todoistShortcuts);
            } finally {
                closeQuietly(standardInputStream);
                closeQuietly(todoistInputStream);
            }
        }

        if (sEmojiPattern == null) {
            StringBuilder patternBuilder = new StringBuilder();

            // Pattern for standard emojis.
            patternBuilder.append("(");
            patternBuilder.append(REGEXP_STANDARD_EMOJI);

            for(String shortcut : todoistShortcuts.keySet()) {
                patternBuilder.append("|");
                patternBuilder.append(Pattern.quote(shortcut));
            }
            // Exclude links ://
            patternBuilder.append(")(?!/)");

            sEmojiPattern = Pattern.compile(patternBuilder.toString());
        }
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e1) { /* Ignore. */ }
        }
    }
}
