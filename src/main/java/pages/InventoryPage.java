package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.NoSuchElementException;

public class InventoryPage extends BasePage{

    public InventoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "select[data-test='product-sort-container']")
    private WebElement selectSortElement;

    @FindBy(css = "[data-test='inventory-list'] [data-test='inventory-item']")
    public List<WebElement> productElements;


    public InventoryPage addProductToCart(String productName)
    {
        WebElement product = getProductByName(productName);
        click(product.findElement(By.cssSelector("button[data-test^='add-to-cart']")));
        return this;
    }

    private WebElement getProductByName(String productName)
    {
        return productElements.stream().filter(e -> e.findElement(By.cssSelector("[data-test='inventory-item-name']"))
                        .getText()
                        .equals(productName))
                        .findFirst()
                        .orElseThrow(() -> new NoSuchElementException(
                        "Cart item with name '" + productName + "' not found"));
    }
}
