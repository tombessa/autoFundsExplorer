package auto.fundsexplorer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FundsPageTest {

    private FirefoxDriver driver;

    @BeforeTest
    public void loadPage() {
        driver = startDriver();
        driver.navigate().to("https://www.fundsexplorer.com.br/funds/mxrf11");
        driver.navigate().to("https://www.fundsexplorer.com.br/wp-json/funds/v1/patrimonials/MXRF11");
    }
    @Test
    public void login() throws IOException {
        System.out.println("");
    }

    @AfterTest
    public void quitPage(){
        driver.quit();
    }

    private FirefoxDriver startDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.setImplicitWaitTimeout(Duration.ofSeconds(30));
        return new FirefoxDriver(options);
    }
}
