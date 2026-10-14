package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    private By nomeDoProdutoNoCarrinho = By.xpath("//td[@class='cart_description']//a");
    // Novo mapeamento: Botão de ir para o checkout
    private By botaoProceedToCheckout = By.cssSelector(".check_out");

    public boolean produtoEstaVisivelNoCarrinho() {
        return driver.findElement(nomeDoProdutoNoCarrinho).isDisplayed();
    }

    public String obterNomeDoProduto() {
        return driver.findElement(nomeDoProdutoNoCarrinho).getText();
    }

    // Nova Ação
    public void clicarProceedToCheckout() {
        driver.findElement(botaoProceedToCheckout).click();
    }
}