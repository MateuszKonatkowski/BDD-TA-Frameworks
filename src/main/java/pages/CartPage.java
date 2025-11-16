package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#checkout")
    private WebElement checkoutButton;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItemElements;

    public CartPage removeProductFromCart(String productName) {
        WebElement product = getCartItemByName(productName);
        product.findElement(By.cssSelector("button[data-test^='remove']")).click();
        return this;
    }

    public CheckoutStepOnePage clickOnCheckoutButton() {
        waitForElementToBeClickable(checkoutButton);
        click(checkoutButton);
        return new CheckoutStepOnePage(driver);
    }

    public boolean cartItemExist(String productName) {
        try {
            getCartItemByName(productName);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private WebElement getCartItemByName(String productName) {
        return cartItemElements.stream().filter(e -> e.findElement(By.cssSelector("[data-test='inventory-item-name']"))
                        .getText()
                        .equals(productName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Cart item with name '" + productName + "' not found"));
    }
}
