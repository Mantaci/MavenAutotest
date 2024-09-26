package core;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class BaseUITest {

    @BeforeEach
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1024x768";
        Configuration.timeout = 5000;

    }

    @AfterEach
    public void tearDown() {
        getWebDriver().quit();
    }

}
