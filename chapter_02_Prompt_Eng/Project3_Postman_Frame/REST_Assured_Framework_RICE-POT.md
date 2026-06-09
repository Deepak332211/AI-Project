# REST Assured API Framework - RICE-POT Prompt

## Objective
Create a production-ready REST Assured API test framework with a Page Object Model (POM) pattern for testing the Restful Booker API. The framework should support data-driven testing, configuration management, comprehensive logging & reporting, request/response validation, and authentication handling—all following enterprise-level best practices.

---

### R — Role
You are a Senior QA Automation Engineer with 10+ years of experience in REST API testing and Java-based test automation frameworks. You are an expert in REST Assured, TestNG, Maven, Page Object Model design patterns, and production-grade testing infrastructure.

### I — Instructions

**Framework Structure & Components:**
1. Create a Maven-based project structure with proper package organization:
   - `base/` — Base classes for test initialization and utilities
   - `pages/` — API endpoint/service classes (POM pattern for APIs)
   - `utils/` — Helper classes for configuration, logging, and API requests
   - `tests/` — Test classes organized by functionality
   - `resources/` — Property files and test data (CSV/JSON)

2. Implement the following core classes:
   - `BaseTest.java` — Setup/teardown, test initialization, listener configuration
   - `APIRequestHelper.java` — Wrapper methods around REST Assured for GET, POST, PUT, DELETE with logging
   - `APIResponseValidator.java` — Methods to validate status code, response body, headers, schema
   - `ConfigReader.java` — Read base URL, API keys, credentials from properties file
   - `APIEndpoints.java` — Constants for all API endpoints
   - `BookerAPIPage.java` (POM class) — Encapsulate Restful Booker API endpoints and operations
   - `LoggerUtil.java` — Centralized logging for all test activities
   - `ReportUtil.java` — Generate test reports with test results summary

3. For data-driven testing:
   - Create test data providers supporting CSV and JSON formats
   - Implement @DataProvider methods for parameterized test execution
   - Store test data in `resources/testdata/` directory

4. For authentication:
   - Support Bearer token authentication
   - Support API Key authentication
   - Store credentials in `config.properties` and load via ConfigReader

5. For request/response validation:
   - Validate HTTP status codes (200, 201, 400, 401, 404, 500)
   - Validate response body structure and values
   - Validate response headers (Content-Type, etc.)
   - Use JSON Schema validation where applicable

6. For logging and reporting:
   - Log all API requests (URL, method, headers, body)
   - Log all API responses (status, body, response time)
   - Generate HTML test reports using TestNG/ExtentReports
   - Include failure screenshots/details for debugging

7. Error handling and exception management:
   - Create custom exceptions for API failures
   - Implement retry logic for flaky tests
   - Provide meaningful error messages for debugging

Do NOT:
- Hard-code URLs, API keys, or credentials in test classes
- Use outdated REST Assured syntax or practices
- Create duplicate validation methods—centralize them
- Skip logging for any API call
- Mix test logic with utility logic
- Create untested utility classes without example usage

### C — Context

**System Context:**
- API: Restful Booker API (free public API for booking management)
- Main endpoints: /auth (login), /booking (CRUD operations)
- Authentication: Bearer token from login response
- Response format: JSON

**Testing Environment:**
- Language: Java
- Framework: REST Assured + TestNG
- Build tool: Maven
- IDE compatibility: IntelliJ IDEA, Eclipse, VS Code
- External libraries: rest-assured, testng, jackson-databind, log4j, gson

**Production Standards:**
- Code follows Java naming conventions and best practices
- All classes are well-documented with JavaDoc comments
- Exception handling is robust and informative
- Configuration is externalized and environment-specific
- All utilities are reusable and DRY (Don't Repeat Yourself)

### E — Example

**Sample Test Case Structure:**
```java
@Test(dataProvider = "bookingData")
public void testCreateBookingWithValidData(String firstName, String lastName, int totalPrice) {
    // 1. Setup: Get auth token
    String token = apiRequestHelper.authenticateUser(username, password);
    
    // 2. Request: Create booking
    Booking bookingPayload = new Booking.Builder()
        .firstname(firstName)
        .lastname(lastName)
        .totalprice(totalPrice)
        .build();
    
    Response response = apiRequestHelper.post("/booking", token, bookingPayload);
    
    // 3. Validate: Response structure and status
    apiResponseValidator.validateStatusCode(response, 200);
    apiResponseValidator.validateResponseBodyNotNull(response);
    
    // 4. Extract: Booking ID from response
    int bookingId = response.jsonPath().getInt("bookingid");
    
    // 5. Verify: Assert booking was created
    Assert.assertTrue(bookingId > 0, "Booking ID should be positive");
}
```

**Sample Data Provider:**
```
firstName,lastName,totalPrice
John,Doe,100
Jane,Smith,200
Bob,Johnson,150
```

### P — Parameters

- **Deterministic Output**: Same input always produces the same output; all random values (IDs, timestamps) are extracted from API responses or externalized
- **Traceability**: Every assertion must reference a specific API response or test data row
- **No Hallucination**: Do not invent API endpoints, response fields, or behavior; use only documented Restful Booker API specs
- **Error Handling**: All exceptions must be caught and logged; no silent failures
- **Code Quality**: All code must be compilable, follow SOLID principles, and include meaningful comments
- **Configuration**: All environment-specific values (base URL, credentials) must be externalized to `config.properties`
- **Reusability**: Helper methods must be generic enough to support multiple API endpoints

### O — Output

**Format:** Java Maven project with complete source code, configuration files, and documentation

**Directory Structure:**
```
RestAssuredFramework/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/qa/api/
│   │   │   ├── base/
│   │   │   │   ├── BaseTest.java
│   │   │   │   └── Listeners.java
│   │   │   ├── pages/
│   │   │   │   ├── BookerAPIPage.java
│   │   │   │   └── APIEndpoints.java
│   │   │   ├── utils/
│   │   │   │   ├── APIRequestHelper.java
│   │   │   │   ├── APIResponseValidator.java
│   │   │   │   ├── ConfigReader.java
│   │   │   │   ├── LoggerUtil.java
│   │   │   │   ├── ReportUtil.java
│   │   │   │   └── DataProviderUtil.java
│   │   │   ├── exceptions/
│   │   │   │   ├── APIException.java
│   │   │   │   └── ValidationException.java
│   │   │   └── models/
│   │   │       ├── Booking.java
│   │   │       └── AuthResponse.java
│   │   └── resources/
│   │       ├── config.properties
│   │       ├── log4j.properties
│   │       └── testdata/
│   │           ├── bookingData.csv
│   │           └── bookingData.json
│   └── test/
│       ├── java/com/qa/api/tests/
│       │   ├── BookingAPITest.java
│       │   └── AuthenticationTest.java
│       └── resources/
│           └── testng.xml
├── testng.xml
└── README.md
```

**Key Files to Generate:**
1. `pom.xml` — Maven dependencies (REST Assured, TestNG, Log4j, Jackson, Gson)
2. `BaseTest.java` — Test setup/teardown with browser/API initialization
3. `BookerAPIPage.java` — API operations encapsulated as "pages" (POM)
4. `APIRequestHelper.java` — Methods for GET, POST, PUT, DELETE with logging
5. `APIResponseValidator.java` — Validation methods for status, body, headers
6. `ConfigReader.java` — Properties file reader
7. `config.properties` — Base URL, API key, credentials, environment config
8. `BookingAPITest.java` — Sample test cases with @DataProvider
9. `README.md` — Setup instructions and framework usage guide
10. `testng.xml` — TestNG configuration for test execution

### T — Tone

**Technical, production-ready, code-focused, minimal commentary.** Output is primarily code with inline JavaDoc comments. Provide executable, copy-paste-ready code that compiles without modification. Include configuration setup instructions where needed but keep narrative brief.

---

## How to Use This Prompt

Paste this prompt into your AI tool (Claude, ChatGPT, Copilot, etc.) along with:
- Optional: Link to Restful Booker API documentation (https://restful-booker.herokuapp.com/apidoc/index.html)
- Request the complete framework with all Java classes

The AI will generate a production-ready REST Assured framework you can directly integrate into your project.
