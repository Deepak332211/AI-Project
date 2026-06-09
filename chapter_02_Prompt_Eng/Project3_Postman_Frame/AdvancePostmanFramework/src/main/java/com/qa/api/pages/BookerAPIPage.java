package com.qa.api.pages;

import com.qa.api.models.AuthResponse;
import com.qa.api.models.Booking;
import com.qa.api.utils.APIRequestHelper;
import com.qa.api.utils.APIResponseValidator;
import com.qa.api.utils.LoggerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

/**
 * BookerAPIPage - Page Object Model for Restful Booker API
 * Encapsulates API operations as "pages" following POM pattern
 * Contains high-level methods for API interactions
 */
public class BookerAPIPage {

    private APIRequestHelper apiRequestHelper;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructor - Initialize BookerAPIPage
     */
    public BookerAPIPage() {
        this.apiRequestHelper = new APIRequestHelper();
    }

    /**
     * Constructor with existing APIRequestHelper
     * @param apiRequestHelper Existing API request helper instance
     */
    public BookerAPIPage(APIRequestHelper apiRequestHelper) {
        this.apiRequestHelper = apiRequestHelper;
    }

    /**
     * Authenticate user and get bearer token
     * @param username Username for authentication
     * @param password Password for authentication
     * @return Authentication token
     */
    public String authenticateUser(String username, String password) {
        LoggerUtil.info("Authenticating user: " + username);
        
        String requestBody = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
        apiRequestHelper.setBody(requestBody);
        
        Response response = apiRequestHelper.post(APIEndpoints.AUTH_ENDPOINT);
        
        APIResponseValidator.validateStatusCode(response, 200);
        APIResponseValidator.validateContentType(response, "application/json");
        
        AuthResponse authResponse = response.as(AuthResponse.class);
        String token = authResponse.getToken();
        
        LoggerUtil.info("Authentication successful. Token: " + token);
        apiRequestHelper.reset();
        return token;
    }

    /**
     * Create a new booking
     * @param booking Booking object with details
     * @return Booking ID of created booking
     */
    public int createBooking(Booking booking) {
        LoggerUtil.info("Creating booking: " + booking);
        
        apiRequestHelper.setBody(booking);
        Response response = apiRequestHelper.post(APIEndpoints.BOOKING_ENDPOINT);
        
        APIResponseValidator.validateStatusCode(response, 200);
        APIResponseValidator.validateContentType(response, "application/json");
        APIResponseValidator.validateResponseBodyNotNull(response);
        
        int bookingId = response.jsonPath().getInt("bookingid");
        LoggerUtil.info("Booking created successfully with ID: " + bookingId);
        
        apiRequestHelper.reset();
        return bookingId;
    }

    /**
     * Get booking by ID
     * @param bookingId Booking ID
     * @return Booking object
     */
    public Booking getBooking(int bookingId) {
        LoggerUtil.info("Fetching booking with ID: " + bookingId);
        
        String endpoint = APIEndpoints.getBookingById(bookingId);
        Response response = apiRequestHelper.get(endpoint);
        
        APIResponseValidator.validateStatusCode(response, 200);
        APIResponseValidator.validateContentType(response, "application/json");
        
        Booking booking = response.as(Booking.class);
        LoggerUtil.info("Booking retrieved: " + booking);
        
        apiRequestHelper.reset();
        return booking;
    }

    /**
     * Get all bookings
     * @return Array of booking IDs
     */
    public int[] getAllBookings() {
        LoggerUtil.info("Fetching all bookings");
        
        Response response = apiRequestHelper.get(APIEndpoints.BOOKING_ENDPOINT);
        
        APIResponseValidator.validateStatusCode(response, 200);
        APIResponseValidator.validateResponseBodyNotNull(response);
        
        int[] bookingIds = response.jsonPath().getArray("$", int[].class);
        LoggerUtil.info("Found " + bookingIds.length + " bookings");
        
        apiRequestHelper.reset();
        return bookingIds;
    }

    /**
     * Update booking with authentication
     * @param bookingId Booking ID to update
     * @param booking Updated booking object
     * @param token Authentication token (cookie)
     * @return Updated booking
     */
    public Booking updateBooking(int bookingId, Booking booking, String token) {
        LoggerUtil.info("Updating booking with ID: " + bookingId);
        
        String endpoint = APIEndpoints.getBookingById(bookingId);
        apiRequestHelper.addHeader("Cookie", "token=" + token);
        apiRequestHelper.setBody(booking);
        
        Response response = apiRequestHelper.put(endpoint);
        
        APIResponseValidator.validateStatusCode(response, 200);
        APIResponseValidator.validateContentType(response, "application/json");
        
        Booking updatedBooking = response.as(Booking.class);
        LoggerUtil.info("Booking updated successfully");
        
        apiRequestHelper.reset();
        return updatedBooking;
    }

    /**
     * Delete booking with authentication
     * @param bookingId Booking ID to delete
     * @param token Authentication token (cookie)
     * @return Response from delete operation
     */
    public Response deleteBooking(int bookingId, String token) {
        LoggerUtil.info("Deleting booking with ID: " + bookingId);
        
        String endpoint = APIEndpoints.getBookingById(bookingId);
        apiRequestHelper.addHeader("Cookie", "token=" + token);
        
        Response response = apiRequestHelper.delete(endpoint);
        
        APIResponseValidator.validateStatusCode(response, 201);
        LoggerUtil.info("Booking deleted successfully");
        
        apiRequestHelper.reset();
        return response;
    }

    /**
     * Partial update of booking (PATCH)
     * @param bookingId Booking ID to update
     * @param booking Partial booking object
     * @param token Authentication token
     * @return Updated booking
     */
    public Booking partialUpdateBooking(int bookingId, Booking booking, String token) {
        LoggerUtil.info("Partially updating booking with ID: " + bookingId);
        
        String endpoint = APIEndpoints.getBookingById(bookingId);
        apiRequestHelper.addHeader("Cookie", "token=" + token);
        apiRequestHelper.setBody(booking);
        
        Response response = apiRequestHelper.patch(endpoint);
        
        APIResponseValidator.validateStatusCode(response, 200);
        APIResponseValidator.validateContentType(response, "application/json");
        
        Booking updatedBooking = response.as(Booking.class);
        LoggerUtil.info("Booking partially updated successfully");
        
        apiRequestHelper.reset();
        return updatedBooking;
    }

    /**
     * Health check (ping)
     * @return Response from ping endpoint
     */
    public Response healthCheck() {
        LoggerUtil.info("Performing health check...");
        
        Response response = apiRequestHelper.get(APIEndpoints.PING_ENDPOINT);
        
        APIResponseValidator.validateStatusCode(response, 201);
        LoggerUtil.info("Health check passed");
        
        apiRequestHelper.reset();
        return response;
    }

    /**
     * Get API request helper instance
     * @return APIRequestHelper instance
     */
    public APIRequestHelper getAPIRequestHelper() {
        return this.apiRequestHelper;
    }

    /**
     * Reset API request helper for fresh state
     */
    public void reset() {
        apiRequestHelper.reset();
    }
}
