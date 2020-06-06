package com.example.farejador;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class WebDriverControl {

    @Value("${webdriver}")
    private String webdriver;

    private WebDriver driver;
    private WebDriverWait wait;

    private WebDriver getWebDriver() throws FileNotFoundException {
        WebDriver driver = null;
        if (StringUtils.contains(webdriver, "chrome")) {
            File file = ResourceUtils.getFile("classpath:chromedriver.exe");
            System.setProperty(webdriver, file.getAbsolutePath());
//            ChromeOptions options = new ChromeOptions();
//            options.addArguments("--headless");
//            return new ChromeDriver(options);
            driver = new ChromeDriver();
        } else if (StringUtils.contains(webdriver, "gecko")) {
            File file = ResourceUtils.getFile("classpath:geckodriver.exe");
            System.setProperty(webdriver, file.getAbsolutePath());
            driver = new FirefoxDriver();
        }

        return driver;
    }

    public void setWebDriverControl() throws FileNotFoundException {
        setDriver(getWebDriver());
        setWait(new WebDriverWait(driver, 20));
    }

}
