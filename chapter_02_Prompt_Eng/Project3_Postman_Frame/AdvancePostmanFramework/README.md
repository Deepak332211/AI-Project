# REST Assured API Testing Framework

A production-ready REST Assured API testing framework with Page Object Model (POM) pattern for testing RESTful APIs (specifically Restful Booker API).

## Features

✅ **Page Object Model (POM)** - Encapsulates API operations following POM design pattern  
✅ **Data-Driven Testing** - Support for CSV and JSON test data  
✅ **Configuration Management** - Externalized configuration via properties files  
✅ **Comprehensive Logging** - Centralized logging for all API interactions  
✅ **Request/Response Validation** - Robust validation methods for API responses  
✅ **Authentication Handling** - Support for Bearer tokens and API keys  
✅ **Error Handling** - Custom exceptions and meaningful error messages  
✅ **Reusable Utilities** - Generic helper classes for common API operations  

## Project Structure

```
AdvancePostmanFramework/
├── src/
│   ├── main/
│   │   ├── java/com/qa/api/
│   │   │   ├── base/
│   │   │   │   └── BaseTest.java              # Base test class with setup/teardown
│   │   │   ├── pages/
│   │   │   │   ├── BookerAPIPage.java         # POM class for API operations
│   │   │   │   └── APIEndpoints.java          # API endpoint constants
│   │   │   ├── utils/
│   │   │   │   ├── APIRequestHelper.java      # REST Assured wrapper for HTTP requests
│   │   │   │   ├── APIResponseValidator.java  # Response validation methods
│   │   │   │   ├── ConfigReader.java          # Configuration file reader
│   │   │   │   └── LoggerUtil.java            # Centralized logging utility
│   │   │   ├── exceptions/
│   │   │   │   ├── APIException.java          # Custom exception for API failures
│   │   │   │   └── ValidationException.java   # Custom exception for validations
│   │   │   └── models/
│   │   │       ├── Booking.java               # POJO for booking data
│   │   │       └── AuthResponse.java          # POJO for auth response
│   │   └── resources/
│   │       ├── config.properties              # Configuration properties
│   │       ├── log4j2.properties              # Logging configuration
│   │       └── testdata/
│   │           └── bookingData.csv            # Test data
│   └── test/
│       ├── java/com/qa/api/tests/
│       │   ├── BookingAPITest.java            # Booking API test cases
│       │   └── AuthenticationTest.java        # Authentication test cases
│       └── resources/
│           └── testng.xml                     # TestNG configuration
└── pom.xml                                    # Maven configuration
```

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Setup Instructions

### 1. Clone or Extract the Project
```bash
cd AdvancePostmanFramework
```

### 2. Update Configuration
Edit `src/main/resources/config.properties`:
```properties
base.url=https://restful-booker.herokuapp.com
username=admin
password=password123
```

### 3. Install Dependencies
```bash
mvn clean install
```

### 4. Run Tests
```bash
# Run all tests
mvn test

# Run specific test suite
mvn test -Dtest=BookingAPITest

# Run with testng.xml
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

## Core Classes

### APIRequestHelper
Wrapper utility for REST Assured providing convenient methods for API requests:
- `get(endpoint)` - Make GET request
- `post(endpoint)` - Make POST request
- `put(endpoint)` - Make PUT request
- `delete(endpoint)` - Make DELETE request
- `addHeader(key, value)` - Add custom header
- `addBearerToken(token)` - Add bearer authentication
- `setBody(body)` - Set request body
- `addQueryParam(key, value)` - Add query parameter

### APIResponseValidator
Utility class for validating API responses:
- `validateStatusCode(response, expectedCode)` - Validate HTTP status
- `validateResponseBodyNotNull(response)` - Check response body exists
- `validateHeaderValue(response, headerName, expectedValue)` - Validate header
- `validateJsonPath(response, jsonPath, expectedValue)` - Validate JSON field
- `validateResponseBodyContains(response, text)` - Check response contains text
- `validateContentType(response, expectedType)` - Validate Content-Type
- `extractJsonValue(response, jsonPath)` - Extract value from response

### BookerAPIPage (POM)
Encapsulates Restful Booker API operations:
- `authenticateUser(username, password)` - Get auth token
- `createBooking(booking)` - Create new booking
- `getBooking(bookingId)` - Retrieve booking
- `getAllBookings()` - Get all bookings
- `updateBooking(bookingId, booking, token)` - Update booking
- `deleteBooking(bookingId, token)` - Delete booking
- `healthCheck()` - API health check

### ConfigReader
Reads configuration from properties file:
- `getProperty(key)` - Get property value
- `getBaseURL()` - Get API base URL
- `getUsername()` / `getPassword()` - Get credentials
- `getConnectionTimeout()` - Get timeout values

### LoggerUtil
Centralized logging utility:
- `info(message)` - Log info level
- `debug(message)` - Log debug level
- `warn(message)` - Log warning level
- `error(message)` - Log error level
- `logAPIRequest(method, url, headers, body)` - Log request details
- `logAPIResponse(statusCode, responseBody, responseTime)` - Log response details

## Usage Examples

### Create and Retrieve a Booking
```java
public class BookingTest extends BaseTest {
    
    @Test
    public void testCreateAndRetrieveBooking() {
        // Create booking
        Booking.BookingDates dates = new Booking.BookingDates("2024-12-15", "2024-12-20");
        Booking booking = new Booking.Builder()
            .firstname("John")
            .lastname("Doe")
            .totalprice(250)
            .depositpaid(true)
            .bookingdates(dates)
            .build();
        
        int bookingId = bookerAPIPage.createBooking(booking);
        
        // Retrieve booking
        Booking retrievedBooking = bookerAPIPage.getBooking(bookingId);
        
        // Validate
        Assert.assertEquals(retrievedBooking.getFirstname(), "John");
        Assert.assertEquals(retrievedBooking.getTotalprice(), 250);
    }
}
```

### Authenticate and Update Booking
```java
@Test
public void testUpdateBookingWithAuth() {
    // Authenticate
    String token = bookerAPIPage.authenticateUser("admin", "password123");
    
    // Update booking
    Booking.BookingDates dates = new Booking.BookingDates("2024-12-20", "2024-12-25");
    Booking updatedBooking = new Booking.Builder()
        .firstname("Jane")
        .lastname("Smith")
        .totalprice(350)
        .depositpaid(true)
        .bookingdates(dates)
        .build();
    
    bookerAPIPage.updateBooking(bookingId, updatedBooking, token);
}
```

### Custom Validation
```java
@Test
public void testBookingValidation() {
    int bookingId = bookerAPIPage.createBooking(booking);
    
    // Validate multiple properties
    APIResponseValidator.validateStatusCode(response, 200);
    APIResponseValidator.validateContentType(response, "application/json");
    APIResponseValidator.validateJsonPath(response, "bookingid", bookingId);
    APIResponseValidator.validateResponseBodyContains(response, "John");
}
```

## Data-Driven Testing

Test data is available in `src/main/resources/testdata/bookingData.csv`:
```csv
firstName,lastName,totalPrice,depositPaid,checkIn,checkOut,additionalNeeds
John,Doe,100,true,2024-12-15,2024-12-20,WiFi needed
Jane,Smith,150,false,2024-12-20,2024-12-25,Late checkout
```

## Logging

Logs are configured in `log4j2.properties`:
- Console logs: Real-time test execution status
- File logs: Saved to `logs/test-execution.log`

Example log output:
```
2024-06-02 10:30:45 [INFO] [BookingAPITest] - >>>>>>>>>> TEST START: testCreateBookingWithValidData >>>>>>>>>>
2024-06-02 10:30:45 [INFO] [APIRequestHelper] - ========== API REQUEST ==========
2024-06-02 10:30:45 [INFO] [APIRequestHelper] - Method: POST
2024-06-02 10:30:45 [INFO] [APIRequestHelper] - URL: https://restful-booker.herokuapp.com/booking
```

## Best Practices

1. **Use POM Pattern** - All API operations are encapsulated in `BookerAPIPage`
2. **Centralize Configuration** - Use `config.properties` for all environment settings
3. **Log Everything** - Use `LoggerUtil` for comprehensive request/response logging
4. **Validate Thoroughly** - Use `APIResponseValidator` for complete response validation
5. **Handle Exceptions** - Custom exceptions provide meaningful error messages
6. **Reuse Code** - Leverage base classes and utilities to reduce duplication
7. **Test Data Management** - Maintain test data in CSV/JSON files
8. **Clean Code** - Follow Java conventions and SOLID principles

## Troubleshooting

### Issue: Tests fail with connection timeout
**Solution**: Increase timeout in `config.properties`:
```properties
connection.timeout=10000
read.timeout=10000
```

### Issue: Cannot find property in config
**Solution**: Ensure `ConfigReader.loadProperties()` is called in setup. The `BaseTest` class handles this automatically.

### Issue: API returning 401 Unauthorized
**Solution**: Verify authentication token in test:
```java
String token = bookerAPIPage.authenticateUser("admin", "password123");
apiRequestHelper.addBearerToken(token);
```

## Dependencies

- **REST Assured** 5.3.1 - HTTP client for API testing
- **TestNG** 7.8.1 - Testing framework
- **Jackson** 2.15.2 - JSON processing
- **Log4j** 2.20.0 - Logging framework
- **Gson** 2.10.1 - JSON serialization
- **JSON Schema Validator** 1.0.73 - Schema validation

## Extension Points

### Add New API Operations
1. Add endpoint constant in `APIEndpoints.java`
2. Add method in `BookerAPIPage.java`
3. Create test class extending `BaseTest`

### Add New Test Data
1. Add CSV rows to `bookingData.csv`
2. Create @DataProvider method in test class
3. Use @Test(dataProvider = "...") annotation

### Add Custom Validations
1. Extend `APIResponseValidator.java`
2. Implement validation logic
3. Call from test classes

## Performance Considerations

- **Connection Pooling**: REST Assured manages connection pooling automatically
- **Request Timeouts**: Configure in `config.properties`
- **Parallel Execution**: Modify `testng.xml` for parallel test runs
- **Logging Impact**: Disable debug logging in production environment

## CI/CD Integration

### Jenkins
```bash
mvn clean test -Denvironment=production
```

### GitHub Actions
```yaml
- name: Run API Tests
  run: mvn clean test
```

### Azure DevOps
```yaml
- task: Maven@3
  inputs:
    mavenPomFile: 'pom.xml'
    goals: 'test'
```

## Contributing

1. Follow existing code structure
2. Add JavaDoc comments for all methods
3. Write meaningful test names
4. Update this README for new features

## License

This framework is provided as-is for educational and testing purposes.

## Support

For issues, questions, or improvements, refer to the documentation or contact the QA team.

---

**Last Updated**: June 2024  
**Framework Version**: 1.0.0  
**Compatibility**: Java 11+, Maven 3.6+
