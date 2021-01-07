package com.example.demo.test;

import lombok.SneakyThrows;
import org.apache.commons.compress.utils.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.example.demo.Constants.TEXT;
import static com.example.demo.Constants.TITLE;

@Component
public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    private final DocumentCreator documentCreator;

    public Parser(DocumentCreator documentCreator) {
        this.documentCreator = documentCreator;
    }

    //    @Scheduled
    @SneakyThrows
    public void parse() {
        logger.info("Start parsing.");
        ChromeOptions opts = new ChromeOptions();
//        opts.setHeadless(true);
        WebDriver driver = new ChromeDriver(opts);
        Actions actions = new Actions(driver);
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



        /*for (int i = 0; i < headers.size(); i++) {


            WebElement link = headers.get(i).findElement(By.className("event__info"));

            if ("Таблица".equals(link.getText())) {
                try {
                    link.click();
                } catch (org.openqa.selenium.StaleElementReferenceException ex) {
//                    link = headers.get(i).findElement(By.className("event__info"));
//                    link.click();
                }
                ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(1));
                String source = driver.getPageSource();
                Document document = Jsoup.parse(source);
                List<Node> rows = document.getElementsByClass("rows___1BdItrT").get(0).childNodes();
                documentCreator.createDocument(i);
                for (int i1 = 0; i1 < rows.size(); i1++) {
                    List<Node> nodeList = rows.get(i1).childNodes();
                    String rowName = nodeList.get(1).childNodes().get(0).childNodes().get(1).childNodes().get(0).attributes().get(TEXT);
                    String E = nodeList.get(2).childNodes().get(0).attributes().get(TEXT);
                    String B = nodeList.get(3).childNodes().get(0).attributes().get(TEXT);
                    String BO = nodeList.get(4).childNodes().get(0).attributes().get(TEXT);
                    String PO = nodeList.get(5).childNodes().get(0).attributes().get(TEXT);
                    String P = nodeList.get(6).childNodes().get(0).attributes().get(TEXT);
                    String BOlong = nodeList.get(7).childNodes().get(0).attributes().get(TEXT);
                    List<Node> nextMatchesTable = nodeList.get(8).childNodes();
                    String nextMatch = nextMatchesTable.get(0).attributes().get(TITLE);
                    String nextMatch2 = nextMatchesTable.get(1).attributes().get(TITLE);
                    String nextMatch3 = nextMatchesTable.get(2).attributes().get(TITLE);
                    String nextMatch4 = nextMatchesTable.get(3).attributes().get(TITLE);
                    String nextMatch5 = nextMatchesTable.get(4).attributes().get(TITLE);
                    String nextMatch6 = nextMatchesTable.get(5).attributes().get(TITLE);

                    documentCreator.addNewRowToFile(rowName, E, B, BO, PO, P, BOlong,
                            nextMatch, nextMatch2, nextMatch3, nextMatch4, nextMatch5, nextMatch6, i1, i);
                }
//                );
//                driver.close();
            }
        }*/
        logger.info("Parsing completed!");
    }
}
