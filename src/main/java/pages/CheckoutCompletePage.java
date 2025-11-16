package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutCompletePage extends BasePage{
    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="back-to-products")
    WebElement backHomeButton;

    @FindBy(css=".complete-header")
    WebElement textHeader;

    @FindBy(css="[data-test='complete-text']")
    WebElement textBody;

    public String getHeaderText()
    {
        return getText(textHeader);
    }

    public InventoryPage clickOnBackHomeButton()
    {
        click(backHomeButton);
        return new InventoryPage(driver);
    }

}
