<h1 align="center"> Parallel Cross Browser Automated Testing  </h1> <br>
<p align="center">
  <img width="350" height="200" src="browsers.png">
</p>
<p align="center">
  Running UI <code>Running cross browser</code> tests - in <b>parallel</b>
</p>

<div align="center">
  <sub>Part of a group of super small repositories demonstrating some of the stuff I have done around _Test Automation_ :necktie:</sub>
</div>


#

# Introduction
A simple demonstration on how to handle the execution of multiple cross-browser automated tests in **PARALLEL**, using `Selenium` and `TestNG`


<p align="center">
  <img width="510" height="300" src="example-execution2.gif" border="3">
</p>

* Example of <code>4</code> tests each scraping information for a different movie from IMDB.com with two tests using `Google Chrome` and two using `Firefox`. 


### Browser Creation
> `BrowserFactory` class handles the creation of browsers.

Each test has the option to either define the browser type required or alternatively it is automatically assigned the default browser. As part of the _test setup_ phase, using the `TestNG @BeforeClass` annotation a call is made to the `BrowserFactory.build()` method which creates a `webdriver` object based on the required browser type. This is then passed to the _browser manager_



### Browser Management
> `BrowserManager` manages each tests browser object.

The _manager_ stores each browser object in a `ThreadLocal` map whereby the maps _<key>_ is the _thread_ itself. When running `TestNG` tests in parallel each test can be set to execute in it's own thread. In this example I added the parallel attribute to the _TestNG.xml_ file
```xml
 <suite name="Showcase" verbose="3" parallel="methods" thread-count="4">
```   
  
When calling the `get()` method on the map, the map will retrieve the browser object for the calling thread

Example storage solution
```Java
private static final ThreadLocal<WebDriver> BROWSER_STORE = new ThreadLocal<WebDriver>();

protected void setup() {
        try {
            final WebDriver driver = browser == null ? BROWSER_FACTORY.buildDriver() : BROWSER_FACTORY.buildDriver(browser);
            BROWSER_STORE.set(driver);

        } catch (final Exception e) {
            fail("driver generation problem encountered");
        }
}
```

<b>NB:</b> Another benefit of such an approach is that it no longer becomes necessary to pass the browser object to each page when using the _Page Object Model_ - each page object could simply just call the `get()` method on the manager to retrieve the browser for the executing test.
```Java
public WebDriver get() {
        return BROWSER_STORE.get();
}
```
