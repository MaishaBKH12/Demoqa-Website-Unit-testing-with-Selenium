import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static java.time.Duration.ofSeconds;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Demoqa {
    WebDriverWait wait;
    WebDriver driver;

    @BeforeAll

    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(ofSeconds(30));

    }

    @Test
    @DisplayName("Get Title")
    public void getTitle() {
        driver.get("https://demoqa.com");
        String title_actual = driver.getTitle();
        System.out.println(title_actual);
    }

    @Test
    @DisplayName("check Image")
    public void CheckImage() {
        driver.get("https://demoqa.com");
        boolean isExists = driver.findElement(By.xpath("//img[@alt='Selenium Online Training']")).isDisplayed();
        Assertions.assertTrue(isExists);

    }

    @Test
    @DisplayName("Fillup Text boxes")
    public void submitForm() {
        driver.get("https://demoqa.com/text-box");
        List<WebElement> txtBoxElement = driver.findElements(By.className("form-control"));
        txtBoxElement.get(0).sendKeys("Maisha Tasnim");
        txtBoxElement.get(1).sendKeys("maisha@test.com");
        txtBoxElement.get(2).sendKeys("Cumilla");
        txtBoxElement.get(3).sendKeys("Cumilla");
        //driver.findElement(By.id("submit")).click();
    }

    @Test
    @DisplayName("Muliple Button Click")
    public void clickOnMultipleButon() {
        driver.get("https://demoqa.com/buttons");
        Actions action = new Actions(driver);
        List<WebElement> list = driver.findElements(By.cssSelector("button"));
        action.doubleClick(list.get(1)).perform();
        String text = driver.findElement(By.id("doubleClickMessage")).getText();
        Assertions.assertTrue(text.contains("You have done a double click"));
        action.contextClick(list.get(2)).perform();
        String text2 = driver.findElement(By.id("rightClickMessage")).getText();
        Assertions.assertTrue(text2.contains("You have done a right click"));
        list.get(3).click();
        String text3 = driver.findElement(By.id("dynamicClickMessage")).getText();
        Assertions.assertTrue(text3.contains("You have done a dynamic click"));
    }

    @Test
    @DisplayName("Alert Button Handling")
    public void handleAlerts() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        //driver.findElement(By.id(("alertButton"))).click();
        //driver.switchTo().alert().accept();
        //driver.switchTo().alert().dismiss();
        driver.findElement(By.id(("promtButton"))).click();
        driver.switchTo().alert().sendKeys("Test");
        Thread.sleep(60);
        driver.switchTo().alert().accept();
        //String text= driver.findElement(By.id("promptResult")).getText();
        //Assertions.assertTrue(text.contains("Test"));
    }

    @Test
    @DisplayName("Select date from Keyboard")
    public void selectDate() {
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(By.id("datePickerMonthYearInput")).click();
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys("05/08/1993", Keys.ENTER);
//        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.ENTER);
    }

    @Test
    @DisplayName("Select Current date")
    public void gettingCurrentDateAndPasteInTheEditableField() {
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(By.id("datePickerMonthYearInput")).click();
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
        // Create object of SimpleDateFormat class and decide the format
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        //get current date time with Date()
        Date date = new Date();
        // Now format the date
        String currentDate = dateFormat.format(date);
        driver.findElement(By.id("datePickerMonthYearInput")).sendKeys(currentDate, Keys.ENTER);
    }

    public void waitForThePresenceOfTheElement(By webElement) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(webElement));
    }

    @Test
    @DisplayName("Select date from Dropdown")
    public void selectingDateFromDropdown() {
        driver.get("https://demoqa.com/date-picker");
        driver.findElement(By.id("datePickerMonthYearInput")).click();
        waitForThePresenceOfTheElement(By.className("react-datepicker__month-select"));
        Select month = new Select(driver.findElement(By.className("react-datepicker__month-select")));
        month.selectByValue("0");
        Select year = new Select(driver.findElement(By.className("react-datepicker__year-select")));
        year.selectByValue("2022");
        driver.findElement(By.xpath("//div[@aria-label='Choose Tuesday, January 4th, 2022']")).click();
    }

    @Test
    @DisplayName("Select Dropdown")
    public void selectDropdown() {
        driver.get("https://demoqa.com/select-menu");
        Select color = new Select(driver.findElement(By.id("oldSelectMenu")));
        color.selectByValue("1");
        Select cars = new Select(driver.findElement(By.id("cars")));
        if (cars.isMultiple()) {
            cars.selectByValue("volvo");
            cars.selectByValue("audi");
        }
    }

    @Test
    @DisplayName("Window handling")
    public void Windows() {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        //Thread.sleep(3000);
        ArrayList<String> tab = new ArrayList(driver.getWindowHandles());
        //switch to open_tab=driver.switchTo().window(w.get(1));
        driver.switchTo().window(tab.get(1));
        String title_actual = driver.findElement(By.id("sampleHeading")).getText();
        System.out.println(title_actual);
        String title_expected = "This is a sample page";
        Assertions.assertTrue(title_actual.contains(title_expected));
        driver.close();
        driver.switchTo().window(tab.get(0));

    }

    @Test
    @DisplayName("Multiple Window Handling")
    public void WindowMultiple() {
        driver.get("https://demoqa.com/browser-windows");
        //Thread.sleep(5000);//WebDriverWait wait = new WebDriverWait(driver, 30);
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
        driver.findElement(By.id(("windowButton"))).click();
        String mainWindowHandle = driver.getWindowHandle();
        System.out.println(mainWindowHandle);
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();
        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)){
            driver.switchTo().window(ChildWindow);
            String text = driver.findElement(By.id("sampleHeading")).getText();
            Assertions.assertTrue(text.contains("This is a sample page"));
        }
    }

        driver.close();
        driver.switchTo().window(mainWindowHandle);

}
    @Test
    @DisplayName("Scrap Data from web")
    public void scrapData(){
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr"));
        int i=0;
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells) {
                i++;
                System.out.println("num["+i+"] "+ cell.getText());

            }
        }
    }

    @Test
    @DisplayName("Handling Frame")
    public void handleIframe(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String text= driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertTrue(text.contains("This is a sample page"));
        driver.switchTo().defaultContent();

    }
    @Test
    public void Menu() {
        driver.get("https://demoqa.com/menu");
        driver.findElement(By.xpath(("//a[normalize-space()='Main Item 1']"))).click();
    }


    @AfterAll
    public void closeDriver(){
    driver.close();

}
}
