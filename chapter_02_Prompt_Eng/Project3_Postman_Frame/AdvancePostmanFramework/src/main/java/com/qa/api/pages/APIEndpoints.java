package com.qa.api.pages;

/**
 * APIEndpoints - Constants for all Restful Booker API endpoints
 * Centralizes endpoint definitions for easy maintenance
 */
public class APIEndpoints {

    public static final String BASE_PATH = "";

    // Authentication Endpoints
    public static final String AUTH_ENDPOINT = "/auth";

    // Booking Endpoints
    public static final String BOOKING_ENDPOINT = "/booking";
    public static final String BOOKING_BY_ID = "/booking/{id}";

    // Ping Endpoint (health check)
    public static final String PING_ENDPOINT = "/ping";

    /**
     * Get booking endpoint with ID
     * @param bookingId Booking ID
     * @return Endpoint URL with ID
     */
    public static String getBookingById(int bookingId) {
        return BOOKING_ENDPOINT + "/" + bookingId;
    }

    /**
     * Get booking endpoint with path parameter
     * @return Endpoint with path parameter placeholder
     */
    public static String getBookingByIdParam() {
        return BOOKING_BY_ID;
    }
}
