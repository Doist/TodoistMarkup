package com.todoist.markup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class EmojiParser {
    private static final String EMOJI_STANDARD = "emojis_standard.json";
    private static final String EMOJI_TODOIST = "emojis_todoist.json";

    private static Map<String, String> sEmojiMap = new HashMap<>();
    private static Pattern sEmojiPattern;

    public static Pattern getEmojiPattern() {
        if (sEmojiPattern == null) {
            try {
                init();
            } catch (IOException e) {
                // Ignore. Not perfect but very unlikely.
            }
        }

        return sEmojiPattern;
    }

    public static String getEmoji(String key) {
        return sEmojiMap.get(key);
    }

    public static synchronized void init() throws IOException {
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
            patternBuilder.append("(:[a-zA-Z\\p{L}0-9_’\\-\\.\\&]+:)");

            if (todoistShortcuts.size() > 0) {
                patternBuilder.append("|");

                Iterator<String> iterator = todoistShortcuts.keySet().iterator();
                while (true) {
                    String s = iterator.next();
                    patternBuilder.append(Pattern.quote(s));

                    if (iterator.hasNext()) {
                        patternBuilder.append("|");
                    } else {
                        break;
                    }
                }
            }

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
