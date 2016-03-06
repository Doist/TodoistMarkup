package com.todoist.markup;

import java.util.regex.Pattern;

class Patterns {
    public static final Pattern HEADER = Pattern.compile("^\\*\\s+");
    public static final Pattern BOLD = Pattern.compile("!!\\s*((?!!!).+?)\\s*!!");
    public static final Pattern ITALIC = Pattern.compile("_\\s*((?!_).+?)\\s*_");
    public static final Pattern INLINE_CODE = Pattern.compile("`\\s*((?!`).+?)\\s*`");
    public static final Pattern CODE_BLOCK = Pattern.compile("```\\s*((?!```).+?)\\s*```", Pattern.DOTALL);
    public static final Pattern LINK = Pattern.compile("((?:[a-zA-Z]+)://[^\\s]+)(?:\\s+\\(([^)]+)\\))?");
    public static final Pattern GMAIL = Pattern.compile("\\[\\[gmail=\\s*(.*?)\\s*,\\s*(.*?)\\s*\\]\\]");
    public static final Pattern OUTLOOK = Pattern.compile("\\[\\[outlook=\\s*(.*?)\\s*,\\s*(.*?)\\s*\\]\\]");
    public static final Pattern THUNDERBIRD =
            Pattern.compile("\\[\\[thunderbird\\n?\\s*([^\\n]+)\\s+([^\\n]+)\\s+\\]\\]");
}
