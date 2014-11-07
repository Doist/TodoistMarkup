package com.todoist.markup;

/**
 * Holds data about specific markup in a given string.
 */
public class MarkupEntry {
    private MarkupType type;
    private int start;
    private int end;
    private String text;
    private String link;

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

    /**
     * Returns the type of markup found.
     */
    public MarkupType type() {
        return type;
    }

    /**
     * Returns where the markup starts in the original string.
     */
    public int start() {
        return start;
    }

    /**
     * Returns where the markup ends in the original string.
     */
    public int end() {
        return end;
    }

    /**
     * Returns any text associated with this marker. For instance, if the markup is a {@link MarkupType#LINK} this will
     * hold its description, if any.
     */
    public String text() {
        return text;
    }

    /**
     * Returns any link associated with this marker. Can be a link, a Gmail email id, etc.
     */
    public String link() {
        return link;
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
