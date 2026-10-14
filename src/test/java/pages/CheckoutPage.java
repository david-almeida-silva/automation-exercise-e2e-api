package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    private By campoComentario = By.cssSelector("textarea[name='message']");
    private By botaoPlaceOrder = By.xpath("//a[contains(@href, '/payment')]");

    public void inserirComentario(String comentario) {
        driver.findElement(campoComentario).sendKeys(comentario);
    }

    public void clicarPlaceOrder() {
        driver.findElement(botaoPlaceOrder).click();
    }
}