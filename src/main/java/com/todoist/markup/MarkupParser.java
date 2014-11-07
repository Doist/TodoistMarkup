package com.todoist.markup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class MarkupParser {
    public static final int HEADER = 1;
    public static final int BOLD = 2;
    public static final int ITALIC = 4;
    public static final int LINK = 8;
    public static final int GMAIL = 16;
    public static final int OUTLOOK = 32;
    public static final int THUNDERBIRD = 64;
    public static final int ALL = 127;

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

            if ((flags & LINK) == LINK) {
                parseLinkMarkupEntries(string, markupEntries);
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
}
