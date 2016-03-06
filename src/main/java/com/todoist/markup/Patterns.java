package com.todoist.markup;

import java.util.regex.Pattern;

class Patterns {
    private static final String PATTERN_BASE = "%s\\s*((?!%s).+?)\\s*%s";

    public static final Pattern HEADER = Pattern.compile("^\\*\\s+");
    public static final Pattern BOLD = Pattern.compile(PATTERN_BASE.replaceAll("%s", "(?:__|!!|\\\\*\\\\*)"));
    public static final Pattern ITALIC = Pattern.compile(PATTERN_BASE.replaceAll("%s", "[_\\*]"));
    public static final Pattern INLINE_CODE = Pattern.compile(PATTERN_BASE.replaceAll("%s", "`"));
    public static final Pattern CODE_BLOCK = Pattern.compile(PATTERN_BASE.replaceAll("%s", "`{3}"), Pattern.DOTALL);
    public static final Pattern LINK = Pattern.compile("((?:[a-zA-Z]+)://[^\\s]+)(?:\\s+\\(([^)]+)\\))?");
    public static final Pattern GMAIL = Pattern.compile("\\[\\[gmail=\\s*(.*?)\\s*,\\s*(.*?)\\s*\\]\\]");
    public static final Pattern OUTLOOK = Pattern.compile("\\[\\[outlook=\\s*(.*?)\\s*,\\s*(.*?)\\s*\\]\\]");
    public static final Pattern THUNDERBIRD =
            Pattern.compile("\\[\\[thunderbird\\n?\\s*([^\\n]+)\\s+([^\\n]+)\\s+\\]\\]");
}
