package com.qa.api.utils;

import com.qa.api.exceptions.APIException;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

/**
 * APIRequestHelper - Wrapper utility for REST Assured operations
 * Provides convenient methods for making API requests with proper logging and error handling
 */
public class APIRequestHelper {

    private RequestSpecification requestSpec;
    private Map<String, String> headers;
    private String baseURL;

    /**
     * Constructor - Initialize API request helper
     */
    public APIRequestHelper() {
        ConfigReader.loadProperties();
        this.baseURL = ConfigReader.getBaseURL();
        this.headers = new HashMap<>();
        this.requestSpec = RestAssured.given();
        setDefaultHeaders();
    }

    /**
     * Set default headers for all requests
     */
    private void setDefaultHeaders() {
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
    }

    /**
     * Add custom header to request
     * @param key Header key
     * @param value Header value
     * @return This object for method chaining
     */
    public APIRequestHelper addHeader(String key, String value) {
        headers.put(key, value);
        requestSpec.header(new Header(key, value));
        LoggerUtil.debug("Header added: " + key + " = " + value);
        return this;
    }

    /**
     * Add bearer token to authorization header
     * @param token Bearer token
     * @return This object for method chaining
     */
    public APIRequestHelper addBearerToken(String token) {
        if (token == null || token.isEmpty()) {
            LoggerUtil.warn("Bearer token is null or empty");
            throw new APIException("Bearer token cannot be null or empty");
        }
        addHeader("Authorization", "Bearer " + token);
        return this;
    }

    /**
     * Add API key to request
     * @param key API key header name
     * @param value API key value
     * @return This object for method chaining
     */
    public APIRequestHelper addAPIKey(String key, String value) {
        addHeader(key, value);
        return this;
    }

    /**
     * Set request body
     * @param body Request body object
     * @return This object for method chaining
     */
    public APIRequestHelper setBody(Object body) {
        requestSpec.body(body);
        return this;
    }

    /**
     * Set query parameter
     * @param key Parameter key
     * @param value Parameter value
     * @return This object for method chaining
     */
    public APIRequestHelper addQueryParam(String key, Object value) {
        requestSpec.queryParam(key, value);
        return this;
    }

    /**
     * Set path parameter
     * @param key Parameter key
     * @param value Parameter value
     * @return This object for method chaining
     */
    public APIRequestHelper addPathParam(String key, Object value) {
        requestSpec.pathParam(key, value);
        return this;
    }

    /**
     * Make GET request
     * @param endpoint API endpoint path
     * @return Response object
     */
    public Response get(String endpoint) {
        long startTime = System.currentTimeMillis();
        try {
            String url = baseURL + endpoint;
            LoggerUtil.logAPIRequest("GET", url, headers, null);
            
            Response response = requestSpec.get(url);
            long responseTime = System.currentTimeMillis() - startTime;
            
            LoggerUtil.logAPIResponse(response.getStatusCode(), response.getBody().asString(), responseTime);
            return response;
        } catch (Exception e) {
            LoggerUtil.error("GET request failed for endpoint: " + endpoint, e);
            throw new APIException("GET request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Make POST request
     * @param endpoint API endpoint path
     * @return Response object
     */
    public Response post(String endpoint) {
        long startTime = System.currentTimeMillis();
        try {
            String url = baseURL + endpoint;
            LoggerUtil.logAPIRequest("POST", url, headers, requestSpec);
            
            Response response = requestSpec.post(url);
            long responseTime = System.currentTimeMillis() - startTime;
            
            LoggerUtil.logAPIResponse(response.getStatusCode(), response.getBody().asString(), responseTime);
            return response;
        } catch (Exception e) {
            LoggerUtil.error("POST request failed for endpoint: " + endpoint, e);
            throw new APIException("POST request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Make PUT request
     * @param endpoint API endpoint path
     * @return Response object
     */
    public Response put(String endpoint) {
        long startTime = System.currentTimeMillis();
        try {
            String url = baseURL + endpoint;
            LoggerUtil.logAPIRequest("PUT", url, headers, requestSpec);
            
            Response response = requestSpec.put(url);
            long responseTime = System.currentTimeMillis() - startTime;
            
            LoggerUtil.logAPIResponse(response.getStatusCode(), response.getBody().asString(), responseTime);
            return response;
        } catch (Exception e) {
            LoggerUtil.error("PUT request failed for endpoint: " + endpoint, e);
            throw new APIException("PUT request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Make DELETE request
     * @param endpoint API endpoint path
     * @return Response object
     */
    public Response delete(String endpoint) {
        long startTime = System.currentTimeMillis();
        try {
            String url = baseURL + endpoint;
            LoggerUtil.logAPIRequest("DELETE", url, headers, null);
            
            Response response = requestSpec.delete(url);
            long responseTime = System.currentTimeMillis() - startTime;
            
            LoggerUtil.logAPIResponse(response.getStatusCode(), response.getBody().asString(), responseTime);
            return response;
        } catch (Exception e) {
            LoggerUtil.error("DELETE request failed for endpoint: " + endpoint, e);
            throw new APIException("DELETE request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Make PATCH request
     * @param endpoint API endpoint path
     * @return Response object
     */
    public Response patch(String endpoint) {
        long startTime = System.currentTimeMillis();
        try {
            String url = baseURL + endpoint;
            LoggerUtil.logAPIRequest("PATCH", url, headers, requestSpec);
            
            Response response = requestSpec.patch(url);
            long responseTime = System.currentTimeMillis() - startTime;
            
            LoggerUtil.logAPIResponse(response.getStatusCode(), response.getBody().asString(), responseTime);
            return response;
        } catch (Exception e) {
            LoggerUtil.error("PATCH request failed for endpoint: " + endpoint, e);
            throw new APIException("PATCH request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Reset request specification for next request
     */
    public void reset() {
        this.requestSpec = RestAssured.given();
        this.headers.clear();
        setDefaultHeaders();
    }

    /**
     * Get current request specification
     * @return RequestSpecification object
     */
    public RequestSpecification getRequestSpec() {
        return this.requestSpec;
    }

    /**
     * Get base URL
     * @return Base URL string
     */
    public String getBaseURL() {
        return this.baseURL;
    }

    /**
     * Set base URL (useful for testing different environments)
     * @param baseURL New base URL
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }
}
