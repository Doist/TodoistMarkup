package com.todoist.markup;

public enum MarkupType {
    /**
     * Headers end with a ":" or start with an "*".
     */
    HEADER,

    /**
     * Bold text is surrounded by "!!".
     */
    BOLD,

    /**
     * Italic text is surrounded by "__".
     */
    ITALIC,

    /**
     * Inline code is surrounded by "`".
     */
    INLINE_CODE,

    /**
     * Code block is surrounded by "```".
     */
    CODE_BLOCK,

    /**
     * Links can be plain links or contain a description using the format "http://domain.com (description)".
     */
    LINK,

    /**
     * Gmail's regular format is "[[gmail=email_id@gmail, description]]".
     */
    GMAIL,

    /**
     * Outlook's regular format is "[[outlook=email_id, description]]".
     */
    OUTLOOK,

    /**
     * Thunderbird's regular format is "[[thunderbird\ndescription\nemail_id\n]]".
     */
    THUNDERBIRD,

    /**
     * Emojis are parsed by {@link EmojiParser}.
     */
    EMOJI
}
