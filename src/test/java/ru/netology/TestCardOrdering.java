package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCardOrdering {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();

    }


    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("enableNetwork", "true");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setVersion("91.0.4472.101");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:7777");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestSuccess() {
        WebElement form = driver.findElement(By.cssSelector("#root > div > form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Золотая Чупакабра");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79745321658");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

//    @Test
//    void shouldTestSuccessV2() {
//        driver.get("http://localhost:7777");
//        WebElement form = driver.findElement(By.cssSelector("#root > div > form"));
//        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Звёздная Чупакабра");
//        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+16947652986");
//        form.findElement(By.cssSelector(".checkbox__box")).click();
//        form.findElement(By.className("button")).click();
//        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
//        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
//    }

    @Test
    void shouldTestInvalidNameWarning() {
        WebElement name = driver.findElement(By.cssSelector("#root > div > form"));
        name.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Looney Tunes");
        name.findElement(By.className("button")).click();
        String text = name.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }

    @Test
    void shouldTestEmptyNameWarning() {
        WebElement name = driver.findElement(By.cssSelector("#root > div > form"));
        name.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        name.findElement(By.className("button")).click();
        String text = name.findElement(By.className("input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());

    }

    @Test
    void shouldTestInvalidTelWarning() {
        WebElement tel = driver.findElement(By.cssSelector("#root > div > form"));
        tel.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Джон Сноу");
        tel.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("09328743897");
        tel.findElement(By.className("button")).click();
        String text = tel.findElement(By.cssSelector("div:nth-child(2) > span > span > span.input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldTestEmptyTelWarning() {
        WebElement tel = driver.findElement(By.cssSelector("#root > div > form"));
        tel.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Джон Сноу");
        tel.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        tel.findElement(By.className("button")).click();
        String text = tel.findElement(By.cssSelector("div:nth-child(2) > span > span > span.input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
}
