package org.easyrules.util;

import java.util.Enumeration;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Utilities class.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Utils {

    public static void muteLoggers() {
        Enumeration<String> loggerNames = LogManager.getLogManager().getLoggerNames();
        while (loggerNames.hasMoreElements()) {
            String loggerName = loggerNames.nextElement();
            if (loggerName.startsWith("org.easyrules")) {
                muteLogger(loggerName);
            }
        }
    }

    private static void muteLogger(String logger) {
        Logger.getLogger(logger).setUseParentHandlers(false);
        Handler[] handlers = Logger.getLogger(logger).getHandlers();
        for (Handler handler : handlers) {
            Logger.getLogger(logger).removeHandler(handler);
        }
    }

}
