package com.qa.api.tests;

import com.qa.api.base.BaseTest;
import com.qa.api.models.Booking;
import com.qa.api.utils.APIResponseValidator;
import com.qa.api.utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * BookingAPITest - Test cases for Booking API operations
 * Tests CRUD operations on bookings
 */
public class BookingAPITest extends BaseTest {

    /**
     * Test: Create a new booking with valid data
     */
    @Test(description = "Create a booking with valid data")
    public void testCreateBookingWithValidData() {
        LoggerUtil.testStart("testCreateBookingWithValidData");

        // Prepare booking data
        Booking.BookingDates bookingDates = new Booking.BookingDates("2024-12-15", "2024-12-20");
        Booking booking = new Booking.Builder()
                .firstname("John")
                .lastname("Doe")
                .totalprice(250)
                .depositpaid(true)
                .bookingdates(bookingDates)
                .additionalneeds("Late checkout")
                .build();

        // Create booking
        int bookingId = bookerAPIPage.createBooking(booking);

        // Verify
        Assert.assertTrue(bookingId > 0, "Booking ID should be greater than 0");
        LoggerUtil.assertion("Booking created successfully with ID: " + bookingId);

        LoggerUtil.testEnd("testCreateBookingWithValidData");
    }

    /**
     * Test: Get booking by ID
     */
    @Test(description = "Get booking by ID")
    public void testGetBookingById() {
        LoggerUtil.testStart("testGetBookingById");

        // Create a booking first
        Booking.BookingDates bookingDates = new Booking.BookingDates("2024-12-15", "2024-12-20");
        Booking booking = new Booking.Builder()
                .firstname("Jane")
                .lastname("Smith")
                .totalprice(300)
                .depositpaid(false)
                .bookingdates(bookingDates)
                .build();

        int bookingId = bookerAPIPage.createBooking(booking);

        // Retrieve the booking
        Booking retrievedBooking = bookerAPIPage.getBooking(bookingId);

        // Verify
        Assert.assertNotNull(retrievedBooking, "Retrieved booking should not be null");
        Assert.assertEquals(retrievedBooking.getFirstname(), "Jane", "First name should match");
        Assert.assertEquals(retrievedBooking.getLastname(), "Smith", "Last name should match");
        Assert.assertEquals(retrievedBooking.getTotalprice(), 300, "Total price should match");

        LoggerUtil.testEnd("testGetBookingById");
    }

    /**
     * Test: Get all bookings
     */
    @Test(description = "Get all bookings")
    public void testGetAllBookings() {
        LoggerUtil.testStart("testGetAllBookings");

        // Get all bookings
        int[] bookingIds = bookerAPIPage.getAllBookings();

        // Verify
        Assert.assertNotNull(bookingIds, "Booking IDs array should not be null");
        Assert.assertTrue(bookingIds.length >= 0, "Should return booking IDs");
        LoggerUtil.assertion("Found " + bookingIds.length + " bookings");

        LoggerUtil.testEnd("testGetAllBookings");
    }

    /**
     * Test: Health check
     */
    @Test(description = "API health check")
    public void testHealthCheck() {
        LoggerUtil.testStart("testHealthCheck");

        // Perform health check
        bookerAPIPage.healthCheck();

        LoggerUtil.assertion("Health check passed");
        LoggerUtil.testEnd("testHealthCheck");
    }

    /**
     * Test: Create booking with minimum required data
     */
    @Test(description = "Create booking with minimum required data")
    public void testCreateBookingMinimalData() {
        LoggerUtil.testStart("testCreateBookingMinimalData");

        // Prepare minimal booking data
        Booking.BookingDates bookingDates = new Booking.BookingDates("2024-12-15", "2024-12-20");
        Booking booking = new Booking.Builder()
                .firstname("Bob")
                .lastname("Johnson")
                .totalprice(150)
                .depositpaid(true)
                .bookingdates(bookingDates)
                .build();

        // Create booking
        int bookingId = bookerAPIPage.createBooking(booking);

        // Verify
        Assert.assertTrue(bookingId > 0, "Booking ID should be positive");
        LoggerUtil.assertion("Booking created with minimal data");

        LoggerUtil.testEnd("testCreateBookingMinimalData");
    }

    /**
     * Test: Create booking with additional needs
     */
    @Test(description = "Create booking with additional needs")
    public void testCreateBookingWithAdditionalNeeds() {
        LoggerUtil.testStart("testCreateBookingWithAdditionalNeeds");

        // Prepare booking with additional needs
        Booking.BookingDates bookingDates = new Booking.BookingDates("2024-12-15", "2024-12-20");
        Booking booking = new Booking.Builder()
                .firstname("Alice")
                .lastname("Brown")
                .totalprice(500)
                .depositpaid(true)
                .bookingdates(bookingDates)
                .additionalneeds("Breakfast included, Airport pickup required")
                .build();

        // Create booking
        int bookingId = bookerAPIPage.createBooking(booking);

        // Retrieve and verify
        Booking retrievedBooking = bookerAPIPage.getBooking(bookingId);
        Assert.assertEquals(retrievedBooking.getAdditionalneeds(), 
                "Breakfast included, Airport pickup required", 
                "Additional needs should match");

        LoggerUtil.testEnd("testCreateBookingWithAdditionalNeeds");
    }
}
