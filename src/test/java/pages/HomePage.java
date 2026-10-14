package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    // Construtor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Mapeamento de Elementos (Locators)
    private By btnSignupLogin = By.xpath("//a[contains(text(), 'Signup / Login')]");
    private By logotipo = By.xpath("//img[@alt='Website for automation practice']");

    // Ações
    public void acessarPaginaInicial() {
        driver.get("https://automationexercise.com/");
    }

    public boolean logotipoEstaVisivel() {
        return driver.findElement(logotipo).isDisplayed();
    }

    public void clicarSignupLogin() {
        driver.findElement(btnSignupLogin).click();
    }
}