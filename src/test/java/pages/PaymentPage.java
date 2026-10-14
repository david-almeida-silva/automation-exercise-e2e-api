package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentPage {
    private WebDriver driver;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    private By campoNomeCartao = By.cssSelector("[data-qa='name-on-card']");
    private By campoNumeroCartao = By.cssSelector("[data-qa='card-number']");
    private By campoCvc = By.cssSelector("[data-qa='cvc']");
    private By campoMesExpiracao = By.cssSelector("[data-qa='expiry-month']");
    private By campoAnoExpiracao = By.cssSelector("[data-qa='expiry-year']");
    private By botaoPagarConfirmar = By.cssSelector("[data-qa='pay-button']");
    
    // Mensagem final de sucesso
    private By mensagemPedidoConfirmado = By.cssSelector("[data-qa='order-placed'] > b");

    public void preencherDadosPagamento(String nome, String numero, String cvc, String mes, String ano) {
        driver.findElement(campoNomeCartao).sendKeys(nome);
        driver.findElement(campoNumeroCartao).sendKeys(numero);
        driver.findElement(campoCvc).sendKeys(cvc);
        driver.findElement(campoMesExpiracao).sendKeys(mes);
        driver.findElement(campoAnoExpiracao).sendKeys(ano);
    }

    public void clicarPagarEConfirmar() {
        driver.findElement(botaoPagarConfirmar).click();
    }

    public String obterTextoPedidoConfirmado() {
        return driver.findElement(mensagemPedidoConfirmado).getText();
    }
}
