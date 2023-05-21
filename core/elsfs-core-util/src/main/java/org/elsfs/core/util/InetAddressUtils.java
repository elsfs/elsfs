package org.elsfs.core.util;

import org.elsfs.core.util.function.FunctionUtils;

import java.net.InetAddress;
import java.net.URL;

/**
 * @author zeng
 * @since 0.0.1
 */
public class InetAddressUtils {

    /**
     * Gets by name.
     * @param urlAddr the host
     * @return the by name
     */
    public static InetAddress getByName(final String urlAddr) {
        return FunctionUtils.doAndHandle(() -> {
            var url = new URL(urlAddr);
            return InetAddress.getByName(url.getHost());
        }, e -> null).get();
    }

    /**
     * Gets cas server host name.
     * @return the cas server host name
     */
    public static String getCasServerHostName() {
        return FunctionUtils.doAndHandle(() -> {
            var hostName = InetAddress.getLocalHost().getHostName();
            var index = hostName.indexOf('.');
            if (index > 0) {
                return hostName.substring(0, index);
            }
            return hostName;
        }, throwable -> "unknown").get();
    }

    /**
     * Gets cas server host address.
     * @param name the name
     * @return the cas server host address
     */
    public static String getCasServerHostAddress(final String name) {
        var host = getByName(name);
        return getByName(name) != null ? host.getHostAddress() : null;
    }

}
