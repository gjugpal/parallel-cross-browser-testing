# Parallel Cross Browser testing
> Quick example of how to create WebDriver instances for cross-browser testing in parallel.

#### Tools
Java 11
Selenium WebDriver
TestNG


### Browser Creation
Browsers are created by the ```DriverFactory``` class


### Browser Management
Each time a browser object is created, it is stored in a thread-safe map. As the map is thread-safe each thread (test) can retrieve it's own browser object.

```
private static final ThreadLocal<WebDriver> THREAD_DRIVER = new ThreadLocal<WebDriver>();

protected void setup() {
        try {
            final WebDriver driver = browser == null ? DRIVER_FACTORY.buildDriver() : DRIVER_FACTORY.buildDriver(browser);
            THREAD_DRIVER.set(driver);

        } catch (final Exception e) {
            fail("driver generation problem encountered");
        }
    }
```
It also means that the WebDriver object does not need to be passed along when using the Page Object Model. As each Page Object can retrieve the 'browser' for the calling test from the map;
```
public WebDriver getDriver() {
        return THREAD_DRIVER.get();
}
```


### Writing a Test
A ```BaseTest``` class using TestNG annotations creates a browser automatically for each test and subsequently closes it after execution. Each test is expected to ```extend``` the BaseTest


#### Using the default browser
The default browser can be passed in as a runtime parameter and thus each test which hasn't defined what browser to use will execute against the default type
```
public class UsingDefaultBrowser extends BaseTest {

    @Test
    public void second() {
        WebDriver driver = getDriver();
        driver.get("http://www.google.com");
    }
}
```

#### Defining the browser as part of the test
```
public class DefiningBrowser extends BaseTest {

@Override
    @BeforeTest
    public void setup() {
        setBrowser(SupportedBrowser.CHROME);
        super.setup();
    }

    @Test
    public void chromeBrowserTest() {
        WebDriver driver = getDriver();
        driver.get("http://www.google.com");
    }
}
```