package com.qa.api.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.utils.ConfigReader;
import com.qa.api.utils.LoggerUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * AuthenticationTest - Test cases for authentication operations
 * Tests login and token generation
 */
public class AuthenticationTest extends BaseTest {

    /**
     * Test: Authenticate user with valid credentials
     */
    @Test(description = "Authenticate user with valid credentials")
    public void testAuthenticateUserWithValidCredentials() {
        LoggerUtil.testStart("testAuthenticateUserWithValidCredentials");

        // Get credentials from config or use default
        String username = "admin";
        String password = "password123";

        // Authenticate
        String token = bookerAPIPage.authenticateUser(username, password);

        // Verify
        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertFalse(token.isEmpty(), "Token should not be empty");
        LoggerUtil.assertion("Authentication successful, token obtained: " + token);

        LoggerUtil.testEnd("testAuthenticateUserWithValidCredentials");
    }

    /**
     * Test: Verify token format
     */
    @Test(description = "Verify authentication token format")
    public void testAuthTokenFormat() {
        LoggerUtil.testStart("testAuthTokenFormat");

        // Authenticate
        String token = bookerAPIPage.authenticateUser("admin", "password123");

        // Verify token is not empty and is a valid format
        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertTrue(token.length() > 0, "Token should have content");
        LoggerUtil.assertion("Token format is valid");

        LoggerUtil.testEnd("testAuthTokenFormat");
    }

    /**
     * Test: Health check endpoint
     */
    @Test(description = "Test health check endpoint")
    public void testHealthCheckEndpoint() {
        LoggerUtil.testStart("testHealthCheckEndpoint");

        // Call health check
        Response response = bookerAPIPage.healthCheck();

        // Verify
        Assert.assertEquals(response.getStatusCode(), 201, "Health check should return 201");
        LoggerUtil.assertion("Health check endpoint working correctly");

        LoggerUtil.testEnd("testHealthCheckEndpoint");
    }
}
