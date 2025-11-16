package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class CheckoutStepTwoPage extends BasePage{

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css="#cancel")
    private WebElement cancelButton;

    @FindBy(css="#finish")
    private WebElement finishButton;

    @FindBy(css="[data-test='payment-info-value']")
    private WebElement paymentInformationText;

    @FindBy(css="[data-test='shipping-info-value']")
    private WebElement shippingInformationText;

    @FindBy(css="[data-test='total-label']")
    private WebElement summaryTotalText;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItemsElements;

    @FindBy(css = "[data-test='total-label']")
    private WebElement totalPrice;

    public InventoryPage clickOnCancelButton()
    {
        click(cancelButton);
        return new InventoryPage(driver);
    }

    public CheckoutCompletePage clickOnFinishButton()
    {
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }

    public Integer getNumberOfItemsInCheckout()
    {
        return cartItemsElements.size();
    }

    public String getCartItemPrice(String productName)
    {
        WebElement product = getCheckoutItemByName(productName);
        return getText(product.findElement(By.cssSelector("[data-test='inventory-item-price']")));
    }

    public String getCartItemDescription(String productName)
    {
        WebElement product = getCheckoutItemByName(productName);
        return getText(product.findElement(By.cssSelector("[data-test='inventory-item-desc']")));
    }

    public String getTotalPrice()
    {
       return getText(totalPrice);
    }

    public boolean checkoutItemExist(String productName)
    {
        try {
            getCheckoutItemByName(productName);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private WebElement getCheckoutItemByName(String productName)
    {
        return cartItemsElements.stream().filter(e -> e.findElement(By.cssSelector("[data-test='inventory-item-name']"))
                        .getText()
                        .equals(productName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Cart item with name '" + productName + "' not found"));
    }
}