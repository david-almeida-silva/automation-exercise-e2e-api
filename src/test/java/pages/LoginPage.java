package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // --- LOCATORS DO LOGIN ---
    private By campoEmailLogin = By.cssSelector("[data-qa='login-email']");
    private By campoSenhaLogin = By.cssSelector("[data-qa='login-password']");
    private By botaoLogin = By.cssSelector("[data-qa='login-button']");
    private By mensagemErro = By.cssSelector(".login-form p");

    // --- LOCATORS DO CADASTRO INICIAL (SIGNUP) ---
    private By campoNomeSignup = By.cssSelector("[data-qa='signup-name']");
    private By campoEmailSignup = By.cssSelector("[data-qa='signup-email']");
    private By botaoSignup = By.cssSelector("[data-qa='signup-button']");

    // --- MÉTODOS DE LOGIN ---
    public void preencherLogin(String email, String senha) {
        driver.findElement(campoEmailLogin).sendKeys(email);
        driver.findElement(campoSenhaLogin).sendKeys(senha);
    }

    public void clicarBotaoLogin() {
        driver.findElement(botaoLogin).click();
    }

    public boolean mensagemDeErroEstaVisivel() {
        return driver.findElement(mensagemErro).isDisplayed();
    }

    public String obterTextoDoErro() {
        return driver.findElement(mensagemErro).getText();
    }

    // --- MÉTODOS DE SIGNUP ---
    public void preencherCadastroInicial(String nome, String email) {
        driver.findElement(campoNomeSignup).sendKeys(nome);
        driver.findElement(campoEmailSignup).sendKeys(email);
    }

    public void clicarBotaoSignup() {
        driver.findElement(botaoSignup).click();
    }
}