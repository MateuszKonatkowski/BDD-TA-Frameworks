package cucumber.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.Adress;
import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.CheckoutStepOnePage;
import pages.CheckoutStepTwoPage;
import pages.HeaderComponent;
import pages.InventoryPage;
import pages.LoginPage;
import util.ConfigReaderUtil;
import util.DriverManager;


import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CartStepDefinitions {

    private final WebDriver driver;

    private final LoginPage loginPage;
    private final InventoryPage inventoryPage;

    private HeaderComponent header;
    private CartPage cart;
    private CheckoutStepOnePage checkoutStepOne;
    private CheckoutStepTwoPage checkoutStepTwo;
    private Adress adress;

    public CartStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage(driver);
        this.inventoryPage = new InventoryPage(driver);
        this.header = new HeaderComponent(driver);
    }

    // ---------------- UC-2: Remove product from cart ----------------

    @Given("User is logged in and on the inventory page")
    public void userIsLoggedInAndOnTheInventoryPage() {
        loginPage
                .enterLogin(ConfigReaderUtil.getChromeProperty("login"))
                .enterPassword(ConfigReaderUtil.getChromeProperty("password"))
                .clickOnLoginButton();

        assertThat(driver.getCurrentUrl()).endsWith("/inventory.html");
    }

    @When("User adds product {string} to the cart")
    public void userAddsProductToTheCart(String productName) {
        inventoryPage.addProductToCart(productName);
    }

    @Then("Cart badge should display {string}")
    public void cartBadgeShouldDisplay(String expectedNumber) {
        header = new HeaderComponent(driver);
        assertThat(header.getCartBadgeNumber()).isEqualTo(expectedNumber);
    }

    @When("User opens the cart")
    public void userOpensTheCart() {
        header = new HeaderComponent(driver);
        cart = header.clickOnCartButton();
    }

    @Then("Cart should contain product {string}")
    public void cartShouldContainProduct(String productName) {
        assertThat(cart.cartItemExist(productName)).isTrue();
    }

    @When("User removes product {string} from the cart")
    public void userRemovesProductFromTheCart(String productName) {
        cart.removeProductFromCart(productName);
    }

    @Then("Cart should not contain product {string}")
    public void cartShouldNotContainProduct(String productName) {
        assertThat(cart.cartItemExist(productName)).isFalse();
    }

    @When("User proceeds to checkout")
    public void userProceedsToCheckout() {
        checkoutStepOne = cart.clickOnCheckoutButton();
    }

    @And("User enters address:")
    public void userEntersAddress(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps(String.class, String.class).get(0);

        Adress adress = new Adress(
                row.get("firstName"),
                row.get("lastName"),
                row.get("postalCode")
        );

        checkoutStepOne = checkoutStepOne.enterAddress(adress);
    }

    @And("User continues to the next checkout step")
    public void userContinuesToTheNextCheckoutStep() {
        checkoutStepTwo = checkoutStepOne.clickOnContinueButton();
    }

    @And("User cancels the checkout")
    public void userCancelsTheCheckout() {
        checkoutStepTwo.clickOnCancelButton();
    }

    @Then("User should be redirected to the inventory page")
    public void userShouldBeRedirectedToTheInventoryPage() {
        assertThat(driver.getCurrentUrl()).endsWith("/inventory.html");
    }
}
