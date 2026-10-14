package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeleteAccountPage {
    private WebDriver driver;

    public DeleteAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    // Mapeamento: Busca o link do menu que contém a rota de deletar conta
    private By menuDeleteAccount = By.xpath("//a[contains(@href, '/delete_account')]");
    
    // Mapeamento: Título de sucesso da exclusão
    private By mensagemContaExcluida = By.cssSelector("[data-qa='account-deleted']");

    public void clicarMenuDeleteAccount() {
        driver.findElement(menuDeleteAccount).click();
    }

    public String obterTextoContaExcluida() {
        return driver.findElement(mensagemContaExcluida).getText();
    }
}
