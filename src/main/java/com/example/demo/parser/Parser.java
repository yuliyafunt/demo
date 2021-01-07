package com.example.demo.parser;

import com.example.demo.excel.DocumentCreator;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    private final DocumentCreator documentCreator;

    public Parser(DocumentCreator documentCreator) {
        this.documentCreator = documentCreator;
    }

    @SneakyThrows
    public void parse() {
        ChromeOptions opts = new ChromeOptions();
//        opts.setHeadless(true);
        WebDriver driver = new ChromeDriver(opts);
        driver.get("https://www.flashscore.ru/basketball/");

        List<WebElement> headers = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("event__header")));

        Optional<String> mainWindow = driver.getWindowHandles().stream().findFirst();
        if (!mainWindow.isPresent()) {
            throw new IllegalStateException("Main window is not found");
        }
        List<WebElement> expanders = driver.findElements(By.className("event__expander"));
        for (WebElement expander : expanders) {
            String aClass = expander.getAttribute("class");
            if (aClass.endsWith("expand")) {
                expander.click();
            }
        }

        for (int i = 0; i < headers.size(); i++) {
            try {
                WebElement header = headers.get(i);
                WebElement element = header.findElement(By.tagName("a"));
                if (element.getText().equals("Таблица")) {
                    element.click();
                }
            } catch (ElementClickInterceptedException e) {
                new WebDriverWait(driver, 10)
                        .pollingEvery(Duration.ofMillis(100))
                        .until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
                WebElement acceptCookies = driver.findElement(By.id("onetrust-accept-btn-handler"));
                acceptCookies.click();
                i--;
            }
        }


        List<WebElement> matches = driver.findElements(By.className("event__match"));
        for (WebElement match : matches) {


        }
        logger.info("Parsing completed!");
    }

    private void parseStanding() {


    }
}
