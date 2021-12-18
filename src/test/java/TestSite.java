import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

class TestSite {
    private static ChromeDriver driver;

    @BeforeAll
    public  static  void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void  manageDriver(){
        driver =new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    }

    @AfterEach
    public void quitDriver() throws InterruptedException {
        driver.quit();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Backbone.js","AngularJS", "Dojo", "React"})
    public void todosTestCase(String technology) throws InterruptedException {
        driver.get("https://todomvc.com/");
        Thread.sleep(3000);
        chooseFramework(technology);
        addTodo("Sleep");
        addTodo("Eat");
        addTodo("Repeat");

        WebElement removedTodo = driver.findElement(By.cssSelector("li:nth-child(2) .toggle"));
        removedTodo.click();
        Thread.sleep(3000);
        assertNumberTest("3");

    }

    @Test
    void doTest()  throws InterruptedException{
        driver.get("https://todomvc.com/");
        Thread.sleep(3000);
        chooseFramework("Backbone.js");
        addTodo("Sleep");
        addTodo("Eat");
        addTodo("Repeat");
        
        WebElement removedTodo= driver.findElement(By.cssSelector("body > section > section > ul > li:nth-child(1) > div > input"));
        removedTodo.click();
        Thread.sleep(3000);
        assertNumberTest("3");
    }

    private void chooseFramework(String technology) throws InterruptedException {
        WebElement technologyLink= driver.findElement(By.linkText(technology));
        technologyLink.click();
        Thread.sleep(3000);
    }

    private void addTodo(String todo){
        WebElement newElement = driver.findElement(By.className("new-todo"));
        newElement.sendKeys(todo);
        newElement.sendKeys(Keys.RETURN);
    }

    private void assertNumberTest(String expectedNumberLeft){
        WebElement numberOfItemsLeft= driver.findElement(By.cssSelector("body > section > footer > span > strong"));
        ExpectedConditions.textToBePresentInElement(numberOfItemsLeft, expectedNumberLeft);
    }

}
