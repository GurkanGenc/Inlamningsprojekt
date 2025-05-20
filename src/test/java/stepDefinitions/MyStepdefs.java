package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyStepdefs {
    String browser = "chrome";
//    String browser = "firefox";
    WebDriver driver;

    private void fillTheForm(String dob, String firstName, String lastName, String email, String password, String confirmPassword)
    {
        driver.findElement(By.id("dp")).sendKeys(dob);

        // To hide the date picker dropdown
        new Actions(driver).click().perform();

        driver.findElement(By.id("member_firstname")).sendKeys(firstName);
        if(lastName != null) {
            driver.findElement(By.id("member_lastname")).sendKeys(lastName);
        }
        driver.findElement(By.id("member_emailaddress")).sendKeys(email);
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys(email);
        driver.findElement(By.id("signupunlicenced_password")).sendKeys(password);
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys(confirmPassword);
    }

    private static void wait(WebDriver driver, By by) {
        new WebDriverWait(driver, Duration.ofSeconds(1))
                .until(ExpectedConditions
                .elementToBeClickable(by));

        driver.findElement(by).click();
    }

    @Given("I navigate to the Basketball England registration page")
    public void iNavigateToTheBasketballEnglandRegistrationPage() {
        if(Objects.equals(browser, "firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver();
        }

        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("I enter valid registration details")
    public void iEnterValidRegistrationDetails() {
        fillTheForm("13/08/1975", "Gürkan", "Jansson Genc", "gurkangenc@hotmail.com", "pass123word!", "pass123word!");
    }

    @When("I accept the terms and conditions")
    public void iAcceptTheTermsAndConditions() {
        driver.findElement(By.cssSelector("label[for='sign_up_25']")).click();
    }

    @When("I accept the terms and conditions with a delay")
    public void iAcceptTheTermsAndConditionsWithADelay() {
        wait(driver, By.cssSelector("label[for='sign_up_25']"));
        driver.findElement(By.cssSelector("label[for='sign_up_25']")).click();
    }

    @When("I accept the age condition")
    public void iAcceptTheAgeCondition() {
        driver.findElement(By.cssSelector("label[for='sign_up_26']")).click();
    }

    @When("I accept the code of ethics and conduct")
    public void iAcceptTheCodeOfEthicsAndConduct() {
        driver.findElement(By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']")).click();
    }

    @When("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        driver.findElement(By.cssSelector("input[type='submit']")).click();
    }

    @Then("I should see a confirmation or be redirected")
    public void iShouldSeeAConfirmationOrBeRedirected() {
        WebElement confirmation = driver.findElement(By.cssSelector("body > div > div.page-content-wrapper > div > h2"));
        String expected = "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";

        String actual = confirmation.getText().trim();

        assertEquals(expected, actual);
    }

    @When("I enter invalid registration details last name {string}")
    public void iEnterInvalidRegistrationDetailsLastName(String lastName) {
        fillTheForm("13/08/1975", "Gürkan", lastName, "gurkangenc@hotmail.com", "pass123word!", "pass123word!");
    }

    @Then("I should see a warning message shows {string}")
    public void iShouldSeeAWarningMessageShows(String expectedWarning) {
        WebElement warning = driver.findElement(By.cssSelector("span[data-valmsg-for='Surname']"));

        String actual = warning.getText();

        assertEquals(expectedWarning, actual);
    }

    @When("I enter invalid registration details confirmation password")
    public void iEnterInvalidRegistrationDetailsConfirmationPassword() {
        fillTheForm("13/08/1975", "Gürkan", "Jansson Genc", "gurkangenc@hotmail.com", "pass123word!", "pass123word");
    }

    @Then("I should see a warning message for wrong confirmation password")
    public void iShouldSeeAWarningMessageForWrongConfirmationPassword() {
        WebElement warning = driver.findElement(By.cssSelector("span[data-valmsg-for='ConfirmPassword']"));
        String expected = "Password did not match";

        String actual = warning.getText();

        assertEquals(expected, actual);
    }

    @When("I do not accept term and conditions")
    public void iDoNotAcceptTermAndConditions() {
        // Intentionally left blank to skip it
    }

    @Then("I should see a warning message for term and conditions")
    public void iShouldSeeAWarningMessageForTermAndConditions() {
        WebElement warning = driver.findElement(By.cssSelector("span[data-valmsg-for='TermsAccept']"));
        String expected = "You must confirm that you have read and accepted our Terms and Conditions";

        String actual = warning.getText();

        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}