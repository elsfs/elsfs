package org.elsfs.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author zeng
 * @since 0.0.1
 */

public class RegexUtils {

    /**
     * A pattern match that does not match anything.
     */
    public static final Pattern MATCH_NOTHING_PATTERN = Pattern.compile("a^");

    /**
     * Creates the pattern with the given flags.
     * @param pattern the pattern, may be null.
     * @param flags the flags
     * @return the compiled pattern or {@link RegexUtils#MATCH_NOTHING_PATTERN} if pattern
     * is null or invalid.
     */
    public static Pattern createPattern(final String pattern, final int flags) {
        if (StringUtils.isBlank(pattern)) {
            return MATCH_NOTHING_PATTERN;
        }
        try {
            return Pattern.compile(pattern, flags);
        }
        catch (final PatternSyntaxException exception) {
            return MATCH_NOTHING_PATTERN;
        }
    }

    /**
     * Creates the pattern. Matching is by default case insensitive.
     * @param pattern the pattern, may not be null.
     * @return the pattern or or {@link RegexUtils#MATCH_NOTHING_PATTERN} if pattern is
     * null or invalid.
     */
    public static Pattern createPattern(final String pattern) {
        return createPattern(pattern, Pattern.CASE_INSENSITIVE);
    }

}
