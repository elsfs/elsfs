package org.elsfs.core.util.time;

import org.elsfs.core.util.function.FunctionUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Objects;

/**
 * @author zeng
 * @since 0.0.1
 */
@SuppressWarnings("all")
public class DateTimeUtils {

    /**
     * Parse the given value as a local datetime. <br/>
     * 将给定的值解析为本地日期时间。
     * @param value the value
     * @return the date/time instance
     */
    public static LocalDateTime localDateTimeOf(final String value) {
        var result = (LocalDateTime) null;

        try {
            result = LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        catch (final Exception e) {
            result = null;
        }

        if (result == null) {
            try {
                result = LocalDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            }
            catch (final Exception e) {
                result = null;
            }
        }

        if (result == null) {
            try {
                result = LocalDateTime.parse(value);
            }
            catch (final Exception e) {
                result = null;
            }
        }

        if (result == null) {
            try {
                result = LocalDateTime.parse(value.toUpperCase(), DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
            }
            catch (final Exception e) {
                result = null;
            }
        }

        if (result == null) {
            try {
                result = LocalDateTime.parse(value.toUpperCase(), DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"));
            }
            catch (final Exception e) {
                result = null;
            }
        }

        if (result == null) {
            try {
                result = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
            }
            catch (final Exception e) {
                result = null;
            }
        }

        if (result == null) {
            try {
                var ld = LocalDate.parse(value, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                result = LocalDateTime.of(ld, LocalTime.now(ZoneId.systemDefault()));
            }
            catch (final Exception e) {
                result = null;
            }
        }

        if (result == null) {
            try {
                var ld = LocalDate.parse(value);
                result = LocalDateTime.of(ld, LocalTime.now(ZoneId.systemDefault()));
            }
            catch (final Exception e) {
                result = null;
            }
        }
        return result;
    }

    /**
     * Convert to zoned date time. <br/>
     * 转换为时区区日期时间。
     * @param value the value
     * @return the zoned date time
     */
    public static ZonedDateTime convertToZonedDateTime(final String value) {
        var dt = zonedDateTimeOf(value);
        if (dt != null) {
            return dt;
        }
        var lt = localDateTimeOf(value);
        return zonedDateTimeOf(lt.atZone(ZoneOffset.UTC));
    }

    /**
     * Parse the given value as a zoned datetime. <br>
     * 将给定的值解析为时区区日期时间。
     * @param value the value
     * @return the date/time instance
     */
    public static ZonedDateTime zonedDateTimeOf(final String value) {
        var parsers = List.of(DateTimeFormatter.ISO_ZONED_DATE_TIME, DateTimeFormatter.RFC_1123_DATE_TIME);
        return parsers.stream()
            .map(parser -> FunctionUtils.doAndHandle(() -> ZonedDateTime.parse(value, parser), throwable -> null).get())
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    /**
     * Utility for creating a ZonedDateTime object from a ZonedDateTime. <br/>
     * 用于从ZonedDateTime创建ZonedDateTime对象的实用工具。
     * @param time ZonedDateTime to be copied
     * @return ZonedDateTime representing time
     */
    public static ZonedDateTime zonedDateTimeOf(final TemporalAccessor time) {
        return ZonedDateTime.from(time);
    }

}
