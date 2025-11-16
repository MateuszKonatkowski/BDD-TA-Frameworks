package test;

import models.Adress;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import pages.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BasicTests extends BaseTest {

    @Test(testName = "UC 1: Successful purchase flow",groups = "Smoke")
    public void successfulPurchaseFlow()
    {
        InventoryPage inventory = new InventoryPage(driver);
        HeaderComponent header = new HeaderComponent(driver);
        Adress address = new Adress("Mateusz","Konatkowski","05520");

        inventory.addProductToCart("Sauce Labs Onesie");
        assertThat(header.getCartBadgeNumber()).isEqualTo("1");

        CartPage cart = header.clickOnCartButton();
        assertThat(driver.getCurrentUrl()).endsWith("/cart.html");
        assertThat(cart.cartItemExist("Sauce Labs Onesie")).isTrue();

        CheckoutStepOnePage checkoutStepOne = cart.clickOnCheckoutButton();
        CheckoutStepTwoPage checkoutStepTwo = checkoutStepOne
                .enterAddress(address)
                .clickOnContinueButton();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(checkoutStepTwo.getNumberOfItemsInCheckout()).isEqualTo(1);
        softly.assertThat(checkoutStepTwo.getCartItemPrice("Sauce Labs Onesie")).contains("7.99");
        softly.assertThat(checkoutStepTwo.getCartItemDescription("Sauce Labs Onesie")).isEqualTo("Rib snap infant onesie for the junior automation engineer in development. Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won't unravel.");
        softly.assertThat(checkoutStepTwo.getTotalPrice()).contains("8.63");
        softly.assertAll();

        CheckoutCompletePage complete = checkoutStepTwo.clickOnFinishButton();
        assertThat(complete.getHeaderText()).isEqualTo("Thank you for your order!");
        complete.clickOnBackHomeButton();

        HeaderComponent headerAfter = new HeaderComponent(driver);
        assertThat(headerAfter.isCartBadgePresent()).isFalse();
        assertThat(driver.getCurrentUrl()).endsWith("/inventory.html");
    }

    @Test(testName = "UC-2: Remove product from cart",groups = "Regression")
    public void removeProductFromCart()
    {
        InventoryPage inventory = new InventoryPage(driver);
        HeaderComponent header = new HeaderComponent(driver);
        Adress address = new Adress("Mateusz","Konatkowski","05520");


        inventory
                .addProductToCart("Sauce Labs Bike Light")
                .addProductToCart("Sauce Labs Fleece Jacket");

        assertThat(header.getCartBadgeNumber()).isEqualTo("2");

        CartPage cart = header.clickOnCartButton();
        assertThat(cart.cartItemExist("Sauce Labs Bike Light")).isTrue();
        assertThat(cart.cartItemExist("Sauce Labs Fleece Jacket")).isTrue();
        cart.removeProductFromCart("Sauce Labs Bike Light");
        assertThat(cart.cartItemExist("Sauce Labs Bike Light")).isFalse();

        HeaderComponent headerAfter = new HeaderComponent(driver);
        assertThat(headerAfter.getCartBadgeNumber()).isEqualTo("1");

        CheckoutStepOnePage checkoutStepOne= cart
                .clickOnCheckoutButton()
                .enterAddress(address);
        CheckoutStepTwoPage checkoutStepTwo = checkoutStepOne.clickOnContinueButton();
        checkoutStepTwo.clickOnCancelButton();
        assertThat(driver.getCurrentUrl()).endsWith("/inventory.html");
    }

    @Test(testName = "UC-3: Checkout validation errors",groups = "Regression")
    public void checkoutValidationErrors()
    {
        InventoryPage inventory = new InventoryPage(driver);
        HeaderComponent header = new HeaderComponent(driver);
        Adress adress = new Adress("Mateusz","Konatkowski","05520");


        inventory.addProductToCart("Sauce Labs Bolt T-Shirt");

        CartPage cart = header.clickOnCartButton();

        CheckoutStepOnePage stepOne = cart.clickOnCheckoutButton();

        stepOne.enterFirstName("").enterLastName("").enterZipCode("").clickOnContinueButton();
        assertThat(stepOne.getErrorMessage()).isEqualTo("Error: First Name is required");

        stepOne.enterFirstName(adress.getFirstName()).clickOnContinueButton();
        assertThat(stepOne.getErrorMessage()).isEqualTo("Error: Last Name is required");

        stepOne.enterLastName(adress.getLastName()).clickOnContinueButton();
        assertThat(stepOne.getErrorMessage()).isEqualTo("Error: Postal Code is required");

        CheckoutStepTwoPage stepTwo = stepOne
                .enterZipCode(adress.getZipCode())
                .clickOnContinueButton();

        assertThat(driver.getCurrentUrl()).endsWith("/checkout-step-two.html");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(stepTwo.getNumberOfItemsInCheckout()).isEqualTo(1);
        softly.assertThat(stepTwo.getTotalPrice()).contains("17.27");
        softly.assertThat(stepTwo.checkoutItemExist("Sauce Labs Bolt T-Shirt")).isTrue();
        softly.assertAll();

        stepTwo.clickOnCancelButton();

        HeaderComponent headerAfter = new HeaderComponent(driver);
        assertThat(headerAfter.getCartBadgeNumber()).isEqualTo("2");
    }
}
