package com.qa.api.utils;

import com.qa.api.exceptions.ValidationException;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

/**
 * APIResponseValidator - Utility class for validating API responses
 * Provides methods to validate status code, response body, headers, and schema
 */
public class APIResponseValidator {

    /**
     * Validate HTTP status code
     * @param response API Response object
     * @param expectedStatusCode Expected HTTP status code
     * @throws ValidationException if status code doesn't match
     */
    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        LoggerUtil.assertion("Validating status code: Expected=" + expectedStatusCode + ", Actual=" + actualStatusCode);
        
        if (actualStatusCode != expectedStatusCode) {
            String error = "Status code mismatch. Expected: " + expectedStatusCode + ", but got: " + actualStatusCode;
            LoggerUtil.error(error);
            throw new ValidationException(error);
        }
        LoggerUtil.info("Status code validation PASSED");
    }

    /**
     * Validate response body is not null
     * @param response API Response object
     * @throws ValidationException if response body is null or empty
     */
    public static void validateResponseBodyNotNull(Response response) {
        String body = response.getBody().asString();
        LoggerUtil.assertion("Validating response body is not null or empty");
        
        if (body == null || body.trim().isEmpty()) {
            String error = "Response body is null or empty";
            LoggerUtil.error(error);
            throw new ValidationException(error);
        }
        LoggerUtil.info("Response body validation PASSED");
    }

    /**
     * Validate response header exists
     * @param response API Response object
     * @param headerName Header name to validate
     * @throws ValidationException if header doesn't exist
     */
    public static void validateHeaderExists(Response response, String headerName) {
        String headerValue = response.getHeader(headerName);
        LoggerUtil.assertion("Validating header exists: " + headerName);
        
        if (headerValue == null || headerValue.isEmpty()) {
            String error = "Header '" + headerName + "' not found in response";
            LoggerUtil.error(error);
            throw new ValidationException(error);
        }
        LoggerUtil.info("Header '" + headerName + "' validation PASSED");
    }

    /**
     * Validate specific response header value
     * @param response API Response object
     * @param headerName Header name
     * @param expectedValue Expected header value
     * @throws ValidationException if header value doesn't match
     */
    public static void validateHeaderValue(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        LoggerUtil.assertion("Validating header: " + headerName + " = " + expectedValue);
        
        if (actualValue == null || !actualValue.equals(expectedValue)) {
            String error = "Header '" + headerName + "' mismatch. Expected: " + expectedValue + ", but got: " + actualValue;
            LoggerUtil.error(error);
            throw new ValidationException(error);
        }
        LoggerUtil.info("Header '" + headerName + "' value validation PASSED");
    }

    /**
     * Validate response body contains specific JSON path value
     * @param response API Response object
     * @param jsonPath JSON path to validate
     * @param expectedValue Expected value at JSON path
     * @throws ValidationException if JSON path value doesn't match
     */
    public static void validateJsonPath(Response response, String jsonPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        LoggerUtil.assertion("Validating JSON path: " + jsonPath + " = " + expectedValue);
        
        if (actualValue == null || !actualValue.toString().equals(expectedValue.toString())) {
            String error = "JSON path '" + jsonPath + "' mismatch. Expected: " + expectedValue + ", but got: " + actualValue;
            LoggerUtil.error(error);
            throw new ValidationException(error);
        }
        LoggerUtil.info("JSON path '" + jsonPath + "' validation PASSED");
    }

    /**
     * Validate response body contains specific text
     * @param response API Response object
     * @param text Text to search for in response body
     * @throws ValidationException if text is not found in response body
     */
    public static void validateResponseBodyContains(Response response, String text) {
        String body = response.getBody().asString();
        LoggerUtil.assertion("Validating response body contains: " + text);
        
        if (!body.contains(text)) {
            String error = "Response body does not contain: '" + text + "'";
            LoggerUtil.error(error);
            throw new ValidationException(error);
        }
        LoggerUtil.info("Response body contains validation PASSED");
    }

    /**
     * Validate response is successful (2xx status code)
     * @param response API Response object
     * @throws ValidationException if response is not successful
     */
    public static void validateResponseIsSuccessful(Response response) {
        int statusCode = response.getStatusCode();
        LoggerUtil.assertion("Validating response is successful (2xx)");
        
        if (statusCode < 200 || statusCode >= 300) {
            String error = "Response is not successful. Status code: " + statusCode;
            LoggerUtil.error(error);
            throw new ValidationException(error);
        }
        LoggerUtil.info("Response success validation PASSED");
    }

    /**
     * Validate response Content-Type header
     * @param response API Response object
     * @param expectedContentType Expected Content-Type (e.g., "application/json")
     * @throws ValidationException if Content-Type doesn't match
     */
    public static void validateContentType(Response response, String expectedContentType) {
        String actualContentType = response.getContentType();
        LoggerUtil.assertion("Validating Content-Type: " + expectedContentType);
        
        if (!actualContentType.contains(expectedContentType)) {
            String error = "Content-Type mismatch. Expected: " + expectedContentType + ", but got: " + actualContentType;
            LoggerUtil.error(error);
            throw new ValidationException(error);
        }
        LoggerUtil.info("Content-Type validation PASSED");
    }

    /**
     * Extract JSON value from response
     * @param response API Response object
     * @param jsonPath JSON path to extract
     * @return Extracted value
     */
    public static Object extractJsonValue(Response response, String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        LoggerUtil.info("Extracted value from JSON path '" + jsonPath + "': " + value);
        return value;
    }

    /**
     * Extract entire response as JSON object
     * @param response API Response object
     * @return Map representation of response body
     */
    public static java.util.Map<String, Object> extractResponseAsMap(Response response) {
        return response.jsonPath().getMap("$");
    }

    /**
     * Validate response time is within acceptable range
     * @param response API Response object
     * @param maxTimeInMillis Maximum acceptable response time in milliseconds
     * @throws ValidationException if response time exceeds maximum
     */
    public static void validateResponseTime(Response response, long maxTimeInMillis) {
        long responseTime = response.getTime();
        LoggerUtil.assertion("Validating response time is under " + maxTimeInMillis + "ms, actual: " + responseTime + "ms");
        
        if (responseTime > maxTimeInMillis) {
            String error = "Response time exceeded. Expected: <" + maxTimeInMillis + "ms, but got: " + responseTime + "ms";
            LoggerUtil.warn(error);
            throw new ValidationException(error);
        }
        LoggerUtil.info("Response time validation PASSED");
    }
}
