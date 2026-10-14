package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        // Configura uma espera inteligente de até 10 segundos para elementos lentos
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Mapeamento
    private By primeiroProdutoAddCart = By.xpath("(//a[@data-product-id='1'])[1]");
    private By linkViewCartModal = By.xpath("//u[contains(text(), 'View Cart')]");

    // Ações
    public void acessarPaginaDeProdutos() {
        // Vai direto pela URL para driblar anúncios que cobrem a tela
        driver.get("https://automationexercise.com/products");
    }

    public void adicionarPrimeiroProdutoAoCarrinho() {
        driver.findElement(primeiroProdutoAddCart).click();
    }

    public void clicarNoModalViewCart() {
        // O Selenium aguarda até o pop-up de sucesso aparecer, e só então clica nele
        wait.until(ExpectedConditions.elementToBeClickable(linkViewCartModal)).click();
    }
}
