package com.todoist.markup;

import org.json.JSONObject;

import java.util.Iterator;
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
        if (sEmojiMap == null) {
            sEmojiMap = new JSONObject(Emojis.getStandard());

            JSONObject todoistEmojis = new JSONObject(Emojis.getTodoist());

            StringBuilder builder = new StringBuilder();
            builder.append("(?<=^|[\\s\\n\\.,;!?\\(\\)])(");
            builder.append(REGEXP_STANDARD_EMOJI);

            Iterator<String> it = (Iterator<String>) todoistEmojis.keys();
            while (it.hasNext()) {
                String key = it.next();

                sEmojiMap.put(key, todoistEmojis.get(key));

                builder.append("|");
                builder.append(Pattern.quote(key));
            }

            builder.append(")");

            sEmojiPattern = Pattern.compile(builder.toString());
        }
    }
}
