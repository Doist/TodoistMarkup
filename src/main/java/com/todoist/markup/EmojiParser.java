package com.todoist.markup;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.regex.Pattern;

class EmojiParser {
    private static final String REGEXP_STANDARD_EMOJI = "(:[a-zA-Z\\p{L}0-9_’\\-\\.\\&]+:)";

    private static final String EMOJIS_STANDARD = "emojis_standard.json";
    private static final String EMOJIS_TODOIST = "emojis_todoist.json";

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
            JSONObject standardEmojis;
            JSONObject todoistEmojis;
            try {
                standardEmojis = new JSONObject(streamToString(EmojiParser.class.getResourceAsStream(EMOJIS_STANDARD)));
                todoistEmojis = new JSONObject(streamToString(EmojiParser.class.getResourceAsStream(EMOJIS_TODOIST)));
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                return;
            }

            sEmojiMap = standardEmojis;

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

    private static String streamToString(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int count;
        while ((count = in.read(buffer)) != -1) {
            out.write(buffer, 0, count);
        }
        try {
            in.close();
        } catch(IOException e) {
            /* Ignore. */
        }
        return out.toString("UTF-8");
    }
}
