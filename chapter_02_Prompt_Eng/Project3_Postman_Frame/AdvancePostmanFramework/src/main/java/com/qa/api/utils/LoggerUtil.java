package com.qa.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LoggerUtil - Centralized logging utility for all test activities
 * Provides convenient methods for logging at different levels
 */
public class LoggerUtil {
    
    private static final Logger logger = LogManager.getLogger(LoggerUtil.class);

    /**
     * Log info level message
     * @param message Message to log
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Log debug level message
     * @param message Message to log
     */
    public static void debug(String message) {
        logger.debug(message);
    }

    /**
     * Log warning level message
     * @param message Message to log
     */
    public static void warn(String message) {
        logger.warn(message);
    }

    /**
     * Log error level message
     * @param message Message to log
     */
    public static void error(String message) {
        logger.error(message);
    }

    /**
     * Log error with exception
     * @param message Message to log
     * @param throwable Exception to log
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Log fatal level message
     * @param message Message to log
     */
    public static void fatal(String message) {
        logger.fatal(message);
    }

    /**
     * Log API request details
     * @param method HTTP method
     * @param url Request URL
     * @param headers Request headers
     * @param body Request body
     */
    public static void logAPIRequest(String method, String url, Object headers, Object body) {
        info("========== API REQUEST ==========");
        info("Method: " + method);
        info("URL: " + url);
        if (headers != null) {
            info("Headers: " + headers.toString());
        }
        if (body != null) {
            info("Body: " + body.toString());
        }
        info("=================================");
    }

    /**
     * Log API response details
     * @param statusCode HTTP status code
     * @param responseBody Response body
     * @param responseTime Response time in milliseconds
     */
    public static void logAPIResponse(int statusCode, Object responseBody, long responseTime) {
        info("========== API RESPONSE ==========");
        info("Status Code: " + statusCode);
        if (responseBody != null) {
            info("Response Body: " + responseBody.toString());
        }
        info("Response Time: " + responseTime + "ms");
        info("==================================");
    }

    /**
     * Log test case start
     * @param testName Name of the test
     */
    public static void testStart(String testName) {
        info(">>>>>>>>>> TEST START: " + testName + " >>>>>>>>>>");
    }

    /**
     * Log test case end
     * @param testName Name of the test
     */
    public static void testEnd(String testName) {
        info("<<<<<<<<<< TEST END: " + testName + " <<<<<<<<<<");
    }

    /**
     * Log assertion
     * @param message Assertion message
     */
    public static void assertion(String message) {
        info("ASSERTION: " + message);
    }
}
