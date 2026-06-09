package com.qa.api.utils;

import org.testng.annotations.DataProvider;
import com.qa.api.models.Booking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DataProviderUtil - Utility class for providing test data from external sources
 * Supports CSV and other data formats
 */
public class DataProviderUtil {

    /**
     * Get booking data from CSV file
     * @return 2D array of booking data for data-driven testing
     */
    @DataProvider(name = "bookingData")
    public static Object[][] getBookingData() {
        String filePath = "src/main/resources/testdata/bookingData.csv";
        List<Object[]> testData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 6) {
                    testData.add(new Object[]{
                            data[0].trim(),  // firstName
                            data[1].trim(),  // lastName
                            Integer.parseInt(data[2].trim()),  // totalPrice
                            Boolean.parseBoolean(data[3].trim()),  // depositPaid
                            data[4].trim(),  // checkIn
                            data[5].trim()   // checkOut
                    });
                }
            }

            LoggerUtil.info("Loaded " + testData.size() + " test data rows from CSV");
        } catch (IOException e) {
            LoggerUtil.error("Error reading CSV file: " + e.getMessage(), e);
            throw new RuntimeException("Failed to load test data from CSV", e);
        }

        return testData.toArray(new Object[0][]);
    }

    /**
     * Create booking from data row
     * @param firstName First name
     * @param lastName Last name
     * @param totalPrice Total price
     * @param depositPaid Deposit paid status
     * @param checkIn Check-in date
     * @param checkOut Check-out date
     * @return Booking object
     */
    public static Booking createBookingFromData(String firstName, String lastName, 
                                                int totalPrice, boolean depositPaid, 
                                                String checkIn, String checkOut) {
        Booking.BookingDates bookingDates = new Booking.BookingDates(checkIn, checkOut);
        return new Booking.Builder()
                .firstname(firstName)
                .lastname(lastName)
                .totalprice(totalPrice)
                .depositpaid(depositPaid)
                .bookingdates(bookingDates)
                .build();
    }
}
