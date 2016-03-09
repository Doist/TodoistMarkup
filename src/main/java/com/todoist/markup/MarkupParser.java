package com.todoist.markup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class MarkupParser {
    /**
     * Returns all {@link MarkupEntry} that matches this {@code string}.
     */
    public static List<MarkupEntry> getMarkupEntries(String string) {
        return getMarkupEntries(string, MarkupFlags.ALL);
    }

    /**
     * Returns all {@link MarkupEntry} of the type(s) set in {@code flags} that matches this {@code string}.
     */
    public static List<MarkupEntry> getMarkupEntries(String string, int flags) {
        List<MarkupEntry> markupEntries = new ArrayList<MarkupEntry>();

        if (string != null) {
            if ((flags & MarkupFlags.HEADER) == MarkupFlags.HEADER) {
                parseHeaderMarkupEntries(string, markupEntries);
            }

            if ((flags & MarkupFlags.BOLD) == MarkupFlags.BOLD) {
                parseBoldMarkupEntries(string, markupEntries);
            }

            if ((flags & MarkupFlags.ITALIC) == MarkupFlags.ITALIC) {
                parseItalicMarkupEntries(string, markupEntries);
            }

            if ((flags & MarkupFlags.LINK) == MarkupFlags.LINK) {
                parseLinkMarkupEntries(string, markupEntries);
            }

            if ((flags & MarkupFlags.GMAIL) == MarkupFlags.GMAIL) {
                parseGmailMarkupEntries(string, markupEntries);
            }

            if ((flags & MarkupFlags.OUTLOOK) == MarkupFlags.OUTLOOK) {
                parseOutlookMarkupEntries(string, markupEntries);
            }

            if ((flags & MarkupFlags.THUNDERBIRD) == MarkupFlags.THUNDERBIRD) {
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
