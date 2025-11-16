package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HeaderComponent extends BasePage{

    public HeaderComponent(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeMenuButton;

    @FindBy(css = "#inventory_sidebar_link")
    private WebElement allItemsSidebarOption;

    @FindBy(css = "#about_sidebar_link")
    private WebElement aboutSidebarOption;

    @FindBy(css = "#logout_sidebar_link")
    private WebElement logoutSidebarOption;

    @FindBy(css = "#reset_sidebar_link")
    private WebElement resetSidebarOption;

    @FindBy(css = "[data-test='shopping-cart-badge']")
    private List<WebElement> shoppingCartBadgeElement;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartButton;

    public HeaderComponent clickOnMenuButton()
    {
        click(menuButton);
        return this;
    }

    public HeaderComponent clickLogoutOption()
    {
        waitForElementToBeClickable(logoutSidebarOption);
        click(logoutSidebarOption);
        return this;
    }

    public String getCartBadgeNumber()
    {
        return shoppingCartBadgeElement.isEmpty() ? null : getText(shoppingCartBadgeElement.get(0));

    }

    public boolean isCartBadgePresent()
    {
        return !shoppingCartBadgeElement.isEmpty() && shoppingCartBadgeElement.get(0).isDisplayed();
    }

    public CartPage clickOnCartButton()
    {
        click(shoppingCartButton);
        return new CartPage(driver);
    }
}
