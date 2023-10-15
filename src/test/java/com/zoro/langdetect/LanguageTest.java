package com.zoro.langdetect;

import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class LanguageTest {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\Selenium-NLP\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.zalando.fr/");


    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void verifyLanguageByTestAnalysis() throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String pageText = (String) js.executeScript("return document.body.innerText;");
//        pageText = pageText.replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll("\\s+", " ").trim();

        String cleanedText = pageText.replaceAll("\\p{So}+", "");
        String[] sentences = cleanedText.split("[.!?]\\s*|\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sentences.txt"))) {
            for (int i = 0; i < sentences.length; i++) {
                writer.write("Sentence[" + i + "]: " + sentences[i]);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int maxSentences = 10;
//        for (String sentence : sentences) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("DetectedLang.txt"))) {
//        for (int k = 0; k < sentences.length; k++) {
//            String sentence = sentences[k];
//            if (sentence.trim().isEmpty() || !sentence.matches(".*[a-zA-Z].*")) {
//                continue;
//            }
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("https://text-analysis12.p.rapidapi.com/language-detection/api/v1.1"))
//                    .header("content-type", "application/json")
//                    .header("X-RapidAPI-Key", "a616725424msh99b036443d486bcp160bdbjsn6b5d4d9b1878")
//                    .header("X-RapidAPI-Host", "text-analysis12.p.rapidapi.com")
//                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"text\": \"" + sentence + "\"}"))
//                    .build();
//            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
////                System.out.println("Sentence: " + sentence + ", Response: " + response.body());
//
//
//
//            String responseBody = response.body();
//            JSONObject jsonResponse = new JSONObject(responseBody);
//            if (!jsonResponse.getBoolean("ok")) {
//                writer.write("Error processing sentence: " + sentence + ", Response: " + responseBody);
//                writer.newLine();
//
//                continue;
//            }
//            String language = jsonResponse.getJSONObject("language_probability").keys().next();
//            double probability = jsonResponse.getJSONObject("language_probability").getDouble(language);
//
//            writer.write(String.format("Sentence[%d]: \"%s\", Language: %s, Probability: %.2f%%", k, sentence, language, probability * 100));
//
//            writer.newLine();
//
//        }
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
}
}

