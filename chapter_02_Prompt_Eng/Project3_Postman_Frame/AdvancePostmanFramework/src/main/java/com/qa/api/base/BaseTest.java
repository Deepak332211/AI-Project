package com.qa.api.base;

import com.qa.api.pages.BookerAPIPage;
import com.qa.api.utils.APIRequestHelper;
import com.qa.api.utils.ConfigReader;
import com.qa.api.utils.LoggerUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

/**
 * BaseTest - Base class for all test classes
 * Provides setup/teardown, test initialization, and common utilities
 */
public class BaseTest {

    protected BookerAPIPage bookerAPIPage;
    protected APIRequestHelper apiRequestHelper;

    /**
     * Setup method - Initialize test environment before each test
     */
    @BeforeClass
    public void setUp() {
        LoggerUtil.info("========== Setting up test environment ==========");
        
        // Load configuration
        ConfigReader.loadProperties();
        
        // Initialize API helpers
        apiRequestHelper = new APIRequestHelper();
        bookerAPIPage = new BookerAPIPage(apiRequestHelper);
        
        LoggerUtil.info("Test environment setup completed");
    }

    /**
     * Teardown method - Clean up after each test
     */
    @AfterClass
    public void tearDown() {
        LoggerUtil.info("========== Tearing down test environment ==========");
        
        // Reset API state
        if (apiRequestHelper != null) {
            apiRequestHelper.reset();
        }
        
        LoggerUtil.info("Test environment teardown completed");
    }

    /**
     * Get BookerAPIPage instance
     * @return BookerAPIPage instance
     */
    public BookerAPIPage getBookerAPIPage() {
        return this.bookerAPIPage;
    }

    /**
     * Get APIRequestHelper instance
     * @return APIRequestHelper instance
     */
    public APIRequestHelper getAPIRequestHelper() {
        return this.apiRequestHelper;
    }
}
