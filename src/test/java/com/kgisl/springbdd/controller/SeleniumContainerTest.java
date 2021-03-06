package com.kgisl.springbdd.controller;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.io.File;

import static org.rnorth.visibleassertions.VisibleAssertions.assertTrue;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

/**
 * Simple example of plain Selenium usage.
 */
public class SeleniumContainerTest {
private static Network net = Network.newNetwork();
@ClassRule
    public static BrowserWebDriverContainer browser = (BrowserWebDriverContainer) new BrowserWebDriverContainer()
            .withDesiredCapabilities(DesiredCapabilities.chrome())
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL, new File("build"))
            .withNetwork(net);
    @Test
    public void simplePlainSeleniumTest() {
        
        RemoteWebDriver driver = browser.getWebDriver();

        
        
      //  RemoteWebDriver driver = chrome.getWebDriver();

        driver.get("https://wikipedia.org");
        WebElement searchInput = driver.findElementByName("search");

        searchInput.sendKeys("Rick Astley");
        searchInput.submit();

        WebElement otherPage = driver.findElementByLinkText("Rickrolling");
        otherPage.click();

        boolean expectedTextFound = driver.findElementsByCssSelector("p")
                .stream()
                .anyMatch(element -> element.getText().contains("meme"));

        assertTrue("The word 'meme' is found on a page about rickrolling", expectedTextFound);
    }
}
