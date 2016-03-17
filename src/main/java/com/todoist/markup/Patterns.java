package com.todoist.markup;

import java.util.regex.Pattern;

class Patterns {
    public static final Pattern HEADER = Pattern.compile("^\\*\\s+");
    // Bold text is wrapped with __ or !! or **
    public static final Pattern BOLD = Pattern.compile("(?:^|[\\s!?,;>:\\(\\)]+)((?:\\*\\*|__|!!)(.*?)(?:\\*\\*|__|!!))(?:$|[\\s!?,;><:\\(\\)]+)", Pattern.MULTILINE);
    // Italic text is wrapped with _ or *
    public static final Pattern ITALIC = Pattern.compile("(?:^|[\\s!?,;>:\\(\\)]+)((?:\\*|_)(.*?)(?:\\*|_))(?:$|[\\s!?,;><:\\(\\)]+)", Pattern.MULTILINE);
    // Inline code is wrapped with `
    public static final Pattern INLINE_CODE = Pattern.compile("`([^`]+)`");
    // Code block is wrapped with ```
    public static final Pattern CODE_BLOCK = Pattern.compile("(?:```)\\s*((?!(?:```)).+?)\\s*(?:```)", Pattern.DOTALL);
    public static final Pattern LINK = Pattern.compile("((?:[a-zA-Z]+)://[^\\s]+)(?:\\s+\\(([^)]+)\\))?");
    public static final Pattern MARKDOWN_LINK = Pattern.compile("\\[(.+?)\\]\\((.+?)\\)");
    public static final Pattern GMAIL = Pattern.compile("\\[\\[gmail=\\s*(.*?)\\s*,\\s*(.*?)\\s*\\]\\]");
    public static final Pattern OUTLOOK = Pattern.compile("\\[\\[outlook=\\s*(.*?)\\s*,\\s*(.*?)\\s*\\]\\]");
    public static final Pattern THUNDERBIRD =
            Pattern.compile("\\[\\[thunderbird\\n?\\s*([^\\n]+)\\s+([^\\n]+)\\s+\\]\\]");
}
