package com.todoist.markup;

/**
 * Holds data about specific markup in a given string.
 */
public class MarkupEntry {
    /**
     * The type of markup found.
     */
    public final MarkupType type;

    /**
     * Where the markup starts in the original string.
     */
    public final int start;

    /**
     * Where the markup ends in the original string.
     */
    public final int end;

    /**
     * The text associated with this markup, if any. For instance, if the markup is a {@link MarkupType#LINK} this will
     * hold its description, if any.
     */
    public final String text;

    /**
     * Returns any link associated with this marker. Can be a link, a Gmail email id, etc.
     */
    public final String link;

    public MarkupEntry(MarkupType type, int start, int end) {
        this(type, start, end, null, null);
    }

    public MarkupEntry(MarkupType type, int start, int end, String text) {
        this(type, start, end, text, null);
    }

    public MarkupEntry(MarkupType type, int start, int end, String text, String link) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.text = text;
        this.link = link;
    }

    @Override
    public String toString() {
        return "MarkupEntry{" +
                "type=" + type +
                ", start=" + start +
                ", end=" + end +
                ", text='" + text + '\'' +
                ", link='" + link + '\'' +
                "}";
    }
}
