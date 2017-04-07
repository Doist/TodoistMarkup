package com.todoist.markup;

import java.util.regex.Pattern;

class Patterns {
    static final Pattern HEADER = Pattern.compile("^\\*\\s(.+)|(.*:)$");
    static final Pattern CODE_BLOCK = Pattern.compile("```([^`{3}]+)```");
    static final Pattern CODE_INLINE = Pattern.compile("`([^`]+)`");
    static final Pattern GMAIL = Pattern.compile("\\[\\[gmail=(.+?),\\s*(.+?)\\]\\]");
    static final Pattern OUTLOOK = Pattern.compile("\\[\\[outlook=(.+?),\\s*(.+?)\\]\\]");
    static final Pattern THUNDERBIRD = Pattern.compile("\\[\\[thunderbird\\n([^\\n]+)\\n([^\\n]+)\\n\\s*\\]\\]");
    static final Pattern MARKDOWN_LINK = Pattern.compile("\\[(.+?)\\]\\((.+?)\\)");
    static final Pattern LINK = Pattern.compile("((?:[a-zA-Z]+)://[^\\s]+)(?:\\s+\\(([^)]+)\\))?");
    static final Pattern BOLD =
            Pattern.compile("(?:^|[\\s!?,;>:\\(\\)]+)((?:\\*\\*|__|!!)(.*?)(?:\\*\\*|__|!!))(?:$|[\\s!?,;><:\\(\\)]+)");
    static final Pattern ITALIC =
            Pattern.compile("(?:^|[\\s!?,;>:\\(\\)]+)((?:\\*|_)(.*?)(?:\\*|_))(?:$|[\\s!?,;><:\\(\\)]+)");
}
