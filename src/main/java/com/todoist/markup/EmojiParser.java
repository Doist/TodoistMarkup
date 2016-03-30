package com.todoist.markup;

import org.json.JSONObject;

import java.util.Set;
import java.util.regex.Pattern;

class EmojiParser {
    private static final String REGEXP_STANDARD_EMOJI = "(:[a-zA-Z\\p{L}0-9_’\\-\\.\\&]+:)";

    private static JSONObject sEmojiMap;
    private static Pattern sEmojiPattern;

    static Pattern getEmojiPattern() {
        if (sEmojiPattern == null) {
            init();
        }

        return sEmojiPattern;
    }

    static String getEmoji(String key) {
        if (sEmojiMap != null && sEmojiMap.has(key)) {
            return sEmojiMap.getString(key);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    static synchronized void init() {
        JSONObject todoistEmojis;
        if (sEmojiMap == null) {
            sEmojiMap = new JSONObject(Emojis.getStandard());

            todoistEmojis = new JSONObject(Emojis.getTodoist());
            for (String key : (Set<String>) todoistEmojis.keySet()) {
                sEmojiMap.put(key, todoistEmojis.get(key));
            }

            StringBuilder patternBuilder = new StringBuilder();
            patternBuilder.append("(?<=^|[\\s\\n\\.,;!?\\(\\)])(");
            patternBuilder.append(REGEXP_STANDARD_EMOJI);
            for (String key : (Set<String>) todoistEmojis.keySet()) {
                patternBuilder.append("|");
                patternBuilder.append(Pattern.quote(key));
            }
            patternBuilder.append(")");

            sEmojiPattern = Pattern.compile(patternBuilder.toString());
        }
    }
}
