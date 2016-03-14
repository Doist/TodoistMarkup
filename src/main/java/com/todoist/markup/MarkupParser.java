package com.todoist.markup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class MarkupParser {
    public static final int HEADER = 1;
    public static final int BOLD = 2;
    public static final int ITALIC = 4;
    public static final int INLINE_CODE = 8;
    public static final int CODE_BLOCK = 16;
    public static final int LINK = 32;
    public static final int MARKDOWN_LINK = 64;
    public static final int GMAIL = 128;
    public static final int OUTLOOK = 256;
    public static final int THUNDERBIRD = 512;
    public static final int EMOJI = 1024;

    public static final int ALL = 2047;

    /**
     * Returns all {@link MarkupEntry} that matches this {@code string}.
     */
    public static List<MarkupEntry> getMarkupEntries(String string) {
        return getMarkupEntries(string, ALL);
    }

    /**
     * Returns all {@link MarkupEntry} of the type(s) set in {@code flags} that matches this {@code string}.
     */
    public static List<MarkupEntry> getMarkupEntries(String string, int flags) {
        List<MarkupEntry> markupEntries = new ArrayList<MarkupEntry>();

        if (string != null) {
            if ((flags & HEADER) == HEADER) {
                parseHeaderMarkupEntries(string, markupEntries);
            }

            if ((flags & BOLD) == BOLD) {
                parseBoldMarkupEntries(string, markupEntries);
            }

            if ((flags & ITALIC) == ITALIC) {
                parseItalicMarkupEntries(string, markupEntries);
            }

            if ((flags & INLINE_CODE) == INLINE_CODE) {
                parseInlineCodeMarkupEntries(string, markupEntries);
            }

            if ((flags & CODE_BLOCK) == CODE_BLOCK) {
                parseCodeBlockMarkupEntries(string, markupEntries);
            }

            if ((flags & LINK) == LINK) {
                parseLinkMarkupEntries(string, markupEntries);
            }

            if ((flags & MARKDOWN_LINK) == MARKDOWN_LINK) {
                parseMarkdownLinkMarkupEntries(string, markupEntries);
            }

            if ((flags & GMAIL) == GMAIL) {
                parseGmailMarkupEntries(string, markupEntries);
            }

            if ((flags & OUTLOOK) == OUTLOOK) {
                parseOutlookMarkupEntries(string, markupEntries);
            }

            if ((flags & THUNDERBIRD) == THUNDERBIRD) {
                parseThunderbirdMarkupEntries(string, markupEntries);
            }

            if ((flags & EMOJI) == EMOJI) {
                parseEmojiMarkupEntries(string, markupEntries);
            }
        }

        return markupEntries;
    }

    private static void parseHeaderMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = Patterns.HEADER.matcher(string);

        if (matcher.find()) {
            markupEntries.add(new MarkupEntry(MarkupType.HEADER, matcher.start(), matcher.end()));
        }
    }

    private static void parseBoldMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = Patterns.BOLD.matcher(string);

        while (matcher.find()) {
            String text = matcher.group(1);
            markupEntries.add(new MarkupEntry(MarkupType.BOLD, matcher.start(), matcher.end(), text));
        }
    }

    private static void parseItalicMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = Patterns.ITALIC.matcher(string);

        while (matcher.find()) {
            String text = matcher.group(1);
            markupEntries.add(new MarkupEntry(MarkupType.ITALIC, matcher.start(), matcher.end(), text));
        }
    }

    private static void parseInlineCodeMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = Patterns.INLINE_CODE.matcher(string);

        while (matcher.find()) {
            String text = matcher.group(1);
            markupEntries.add(new MarkupEntry(MarkupType.INLINE_CODE, matcher.start(), matcher.end(), text));
        }
    }

    private static void parseCodeBlockMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = Patterns.CODE_BLOCK.matcher(string);

        while (matcher.find()) {
            String text = matcher.group(1);
            markupEntries.add(new MarkupEntry(MarkupType.CODE_BLOCK, matcher.start(), matcher.end(), text));
        }
    }

    private static void parseLinkMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = Patterns.LINK.matcher(string);

        while (matcher.find()) {
            String text = matcher.group(2);
            String link = matcher.group(1);

            if (text == null) {
                link = link != null ? link : matcher.group(0);
                markupEntries.add(new MarkupEntry(MarkupType.LINK, matcher.start(), matcher.end(), link, link));
            } else {
                markupEntries.add(new MarkupEntry(MarkupType.LINK, matcher.start(), matcher.end(), text, link));
            }
        }
    }

    private static void parseMarkdownLinkMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = Patterns.MARKDOWN_LINK.matcher(string);

        while (matcher.find()) {
            String text = matcher.group(1);
            String link = matcher.group(2);
            markupEntries.add(new MarkupEntry(MarkupType.LINK, matcher.start(), matcher.end(), text, link));
        }
    }

    private static void parseGmailMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = Patterns.GMAIL.matcher(string);

        while (matcher.find()) {
            String text = matcher.group(2);
            String link = matcher.group(1);
            markupEntries.add(new MarkupEntry(MarkupType.GMAIL, matcher.start(), matcher.end(), text, link));
        }
    }

    private static void parseOutlookMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = Patterns.OUTLOOK.matcher(string);

        while (matcher.find()) {
            String text = matcher.group(2);
            String link = matcher.group(1);
            markupEntries.add(new MarkupEntry(MarkupType.OUTLOOK, matcher.start(), matcher.end(), text, link));
        }
    }

    private static void parseThunderbirdMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = Patterns.THUNDERBIRD.matcher(string);

        while (matcher.find()) {
            String text = matcher.group(1);
            String link = matcher.group(2);
            markupEntries.add(new MarkupEntry(MarkupType.THUNDERBIRD, matcher.start(), matcher.end(), text, link));
        }
    }

    private static void parseEmojiMarkupEntries(String string, List<MarkupEntry> markupEntries) {
        Matcher matcher = EmojiParser.getEmojiPattern().matcher(string);

        while (matcher.find()) {
            String emoji = getEmoji(matcher.group());
            if(emoji != null) {
                markupEntries.add(new MarkupEntry(MarkupType.EMOJI, matcher.start(), matcher.end(), emoji));
            }
        }
    }

    public static String getEmoji(String key) {
        return EmojiParser.getEmoji(key);
    }

    public static void initAsync() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EmojiParser.init();
                } catch (IOException e) {
                    // Ignore. Very unlikely.
                }
            }
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }
}
