import com.todoist.markup.MarkupEntry;
import com.todoist.markup.MarkupParser;
import com.todoist.markup.MarkupType;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class MarkupTest {

    @Test
    public void headers() {
        List<MarkupEntry> markupEntries;

        // Regular header.
        markupEntries = MarkupParser.getMarkupEntries("Header:", MarkupParser.HEADER);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.HEADER, 0, 7, "Header:", null)));

        // Header with only a colon.
        markupEntries = MarkupParser.getMarkupEntries(":", MarkupParser.HEADER);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.HEADER, 0, 1, ":", null)));

        // Header with space.
        markupEntries = MarkupParser.getMarkupEntries("* Header", MarkupParser.HEADER);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.HEADER, 0, 8, "Header", null)));

        // Header with both syntaxes.
        markupEntries = MarkupParser.getMarkupEntries("* Double header:", MarkupParser.HEADER);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.HEADER, 0, 16, "Double header:", null)));

        // Not a header, no whitespace after asterisk.
        markupEntries = MarkupParser.getMarkupEntries("*Header", MarkupParser.HEADER);
        assertThat(markupEntries, is(empty()));

        // Not a header, the asterisk is in the middle of the text.
        markupEntries = MarkupParser.getMarkupEntries("Text * NotHeader", MarkupParser.HEADER);
        assertThat(markupEntries, is(empty()));

        // Not a header, only an asterisk.
        markupEntries = MarkupParser.getMarkupEntries("*", MarkupParser.HEADER);
        assertThat(markupEntries, is(empty()));
    }

    @Test
    public void bolds() {
        List<MarkupEntry> markupEntries;

        // Bold text isolated.
        markupEntries = MarkupParser.getMarkupEntries("!!bold text!!", MarkupParser.BOLD);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.BOLD, 0, 13, "bold text", null)));

        // Bold text in the middle of regular text.
        markupEntries = MarkupParser.getMarkupEntries("A !!bold text!! test", MarkupParser.BOLD);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.BOLD, 2, 15, "bold text", null)));

        // Bold text ending with exclamation point.
        markupEntries = MarkupParser.getMarkupEntries("!!bold text!!!", MarkupParser.BOLD);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.BOLD, 0, 13, "bold text", null)));

        // Bold text ending with exclamation point after regular text.
        markupEntries = MarkupParser.getMarkupEntries("Some !!bold text!!!", MarkupParser.BOLD);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.BOLD, 5, 18, "bold text", null)));

        // Invalid bold text, as the closing !! is incomplete.
        markupEntries = MarkupParser.getMarkupEntries("!!not bold text!", MarkupParser.BOLD);
        assertThat(markupEntries, is(empty()));
    }

    @Test
    public void italics() {
        List<MarkupEntry> markupEntries;

        // Italic text isolated.
        markupEntries = MarkupParser.getMarkupEntries("__italic text__", MarkupParser.ITALIC);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.ITALIC, 0, 15, "italic text", null)));

        // Italic text in the middle of regular text.
        markupEntries = MarkupParser.getMarkupEntries("An __italic text__ test", MarkupParser.ITALIC);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.ITALIC, 3, 18, "italic text", null)));

        // Italic text ending with an underscore.
        markupEntries = MarkupParser.getMarkupEntries("__italic text___", MarkupParser.ITALIC);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.ITALIC, 0, 15, "italic text", null)));

        // Italic text ending with an underscore after regular text.
        markupEntries = MarkupParser.getMarkupEntries("Some __italic text___", MarkupParser.ITALIC);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.ITALIC, 5, 20, "italic text", null)));

        // Invalid italic text, as the closing __ is incomplete.
        markupEntries = MarkupParser.getMarkupEntries("__not italic text_", MarkupParser.BOLD);
        assertThat(markupEntries, is(empty()));
    }

    @Test
    public void links() {
        List<MarkupEntry> markupEntries;

        // A link with description.
        markupEntries = MarkupParser.getMarkupEntries("http://google.com/ (Google)", MarkupParser.LINK);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.LINK, 0, 27, "Google", "http://google.com/")));

        // A more complex link with a description.
        markupEntries = MarkupParser.getMarkupEntries("https://mail.google.com/mail/#inbox/138ffe4d68761a59 (Gmail)",
                                                      MarkupParser.LINK);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0),
                   is(equivalentTo(MarkupType.LINK, 0, 60, "Gmail",
                                   "https://mail.google.com/mail/#inbox/138ffe4d68761a59")));

        // Just a link.
        markupEntries = MarkupParser.getMarkupEntries("http://google.com/", MarkupParser.LINK);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0),
                   is(equivalentTo(MarkupType.LINK, 0, 18, "http://google.com/", "http://google.com/")));

        // Just a link with additional parts.
        markupEntries = MarkupParser.getMarkupEntries("https://mail.google.com/mail/#inbox/138ffe4d68761a59",
                                                      MarkupParser.LINK);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0),
                   is(equivalentTo(MarkupType.LINK, 0, 52, "https://mail.google.com/mail/#inbox/138ffe4d68761a59",
                                   "https://mail.google.com/mail/#inbox/138ffe4d68761a59")));

        // A link preceded by text.
        markupEntries = MarkupParser.getMarkupEntries("Link: http://google.com", MarkupParser.LINK);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0),
                   is(equivalentTo(MarkupType.LINK, 6, 23, "http://google.com", "http://google.com")));

        // A link with description preceded by text.
        markupEntries = MarkupParser.getMarkupEntries("Link: http://google.com (Google)", MarkupParser.LINK);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.LINK, 6, 32, "Google", "http://google.com")));

        // Not a link, just a German word.
        markupEntries = MarkupParser.getMarkupEntries("Haftpflichtversicherung", MarkupParser.LINK);
        assertThat(markupEntries, is(empty()));
        assertThat(markupEntries, is(empty()));
    }

    @Test
    public void gmail() {
        List<MarkupEntry> markupEntries;

        // A Gmail mark.
        markupEntries = MarkupParser.getMarkupEntries("[[gmail=138ffe4d68761a60@gmail, Gmail]]", MarkupParser.GMAIL);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.GMAIL, 0, 39, "Gmail", "138ffe4d68761a60@gmail")));

        // A Gmail mark preceded by text.
        markupEntries = MarkupParser.getMarkupEntries("Check back on [[gmail=138ffe4d68761a61@gmail, Todo]]",
                                                      MarkupParser.GMAIL);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.GMAIL, 14, 52, "Todo", "138ffe4d68761a61@gmail")));
    }

    @Test
    public void outlook() {
        List<MarkupEntry> markupEntries;

        // An Outlook mark.
        markupEntries = MarkupParser.getMarkupEntries("[[outlook=138ffe4d68761a60, Outlook]]", MarkupParser.OUTLOOK);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.OUTLOOK, 0, 37, "Outlook", "138ffe4d68761a60")));

        // An Outlook preceded by text.
        markupEntries = MarkupParser.getMarkupEntries("Check back on [[outlook=138ffe4d68761a61, Todo]]",
                                                      MarkupParser.OUTLOOK);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0), is(equivalentTo(MarkupType.OUTLOOK, 14, 48, "Todo", "138ffe4d68761a61")));
    }

    @Test
    public void thunderbird() {
        List<MarkupEntry> markupEntries;

        // An Thunderbird mark.
        markupEntries =
                MarkupParser.getMarkupEntries(
                        "[[thunderbird\nThunderbird\n04ACFBD9-BE56-48EB-96DA-BC2203A0DAAD@someemail.com\n]]",
                        MarkupParser.THUNDERBIRD);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0),
                   is(equivalentTo(MarkupType.THUNDERBIRD, 0, 79, "Thunderbird",
                                   "04ACFBD9-BE56-48EB-96DA-BC2203A0DAAD@someemail.com")));

        // An Thunderbird mark preceded by text.
        markupEntries =
                MarkupParser.getMarkupEntries(
                        "Check [[thunderbird\nTodo\n04ACFBD9-BE56-48EB-96DA-BC2203A0DAAE@someemail.com\n]]",
                        MarkupParser.THUNDERBIRD);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0),
                   is(equivalentTo(MarkupType.THUNDERBIRD, 6, 78, "Todo",
                                   "04ACFBD9-BE56-48EB-96DA-BC2203A0DAAE@someemail.com")));
    }

    @Test
    public void mix() {
        List<MarkupEntry> markupEntries;

        // A mix of markup types, including bold inside italic.
        markupEntries =
                MarkupParser.getMarkupEntries(
                        "* A !!very!! __important__ http://android.com (link) is __!!important!!__", MarkupParser.ALL);
        assertThat(markupEntries, hasSize(6));
        assertThat(markupEntries,
                   containsInAnyOrder(equivalentTo(MarkupType.HEADER, 0, 2, null, null),
                                      equivalentTo(MarkupType.BOLD, 4, 12, "very", null),
                                      equivalentTo(MarkupType.ITALIC, 13, 26, "important", null),
                                      equivalentTo(MarkupType.LINK, 27, 52, "link", "http://android.com"),
                                      equivalentTo(MarkupType.ITALIC, 56, 73, "!!important!!", null),
                                      equivalentTo(MarkupType.BOLD, 58, 71, "important", null)));

        // Another mix of markup types, this time with bold inside a link description.
        markupEntries = MarkupParser.getMarkupEntries("Or even http://android.com (!!important link!!)");
        assertThat(markupEntries, hasSize(2));
        assertThat(markupEntries,
                   containsInAnyOrder(equivalentTo(MarkupType.LINK, 8, 47, "!!important link!!", "http://android.com"),
                                      equivalentTo(MarkupType.BOLD, 28, 46, "important link", null)));
    }

    @Test
    public void flags() {
        List<MarkupEntry> markupEntries;
        String string = "* A !!very!! __important__ http://android.com (link) is __!!important!!__";

        markupEntries = MarkupParser.getMarkupEntries(string, MarkupParser.HEADER);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0).type, is(MarkupType.HEADER));

        markupEntries = MarkupParser.getMarkupEntries(string, MarkupParser.BOLD);
        assertThat(markupEntries, hasSize(2));
        assertThat(markupEntries.get(0).type, is(MarkupType.BOLD));
        assertThat(markupEntries.get(1).type, is(MarkupType.BOLD));

        markupEntries = MarkupParser.getMarkupEntries(string, MarkupParser.ITALIC);
        assertThat(markupEntries, hasSize(2));
        assertThat(markupEntries.get(0).type, is(MarkupType.ITALIC));
        assertThat(markupEntries.get(1).type, is(MarkupType.ITALIC));

        markupEntries = MarkupParser.getMarkupEntries(string, MarkupParser.LINK);
        assertThat(markupEntries, hasSize(1));
        assertThat(markupEntries.get(0).type, is(MarkupType.LINK));

        markupEntries = MarkupParser.getMarkupEntries(string, MarkupParser.GMAIL);
        assertThat(markupEntries, is(empty()));

        markupEntries = MarkupParser.getMarkupEntries(string, MarkupParser.OUTLOOK);
        assertThat(markupEntries, is(empty()));

        markupEntries = MarkupParser.getMarkupEntries(string, MarkupParser.THUNDERBIRD);
        assertThat(markupEntries, is(empty()));
    }

    private Matcher<MarkupEntry> equivalentTo(final MarkupType type, final int start, final int end,
                                              final String text, final String link) {
        return new TypeSafeMatcher<MarkupEntry>() {
            @Override
            protected boolean matchesSafely(MarkupEntry entry) {
                return entry.type == type && entry.start == start && entry.end == end &&
                        (entry.text == null && text == null || (entry.text != null && entry.text.equals(text))) &&
                        (entry.link == null && link == null || (entry.link != null && entry.link.equals(link)));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(new MarkupEntry(type, start, end, text, link).toString());
            }
        };
    }
}
