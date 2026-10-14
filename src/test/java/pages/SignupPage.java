package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage {
    private WebDriver driver;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
    }

    // Mapeamento dos campos usando os atributos data-qa (fortes) e IDs
    private By radioGeneroMr = By.id("id_gender1");
    private By campoSenha = By.cssSelector("[data-qa='password']");
    private By campoPrimeiroNome = By.cssSelector("[data-qa='first_name']");
    private By campoUltimoNome = By.cssSelector("[data-qa='last_name']");
    private By campoEndereco = By.cssSelector("[data-qa='address']");
    private By campoEstado = By.cssSelector("[data-qa='state']");
    private By campoCidade = By.cssSelector("[data-qa='city']");
    private By campoCep = By.cssSelector("[data-qa='zipcode']");
    private By campoCelular = By.cssSelector("[data-qa='mobile_number']");
    private By botaoCriarConta = By.cssSelector("[data-qa='create-account']");
    
    // Mensagem de sucesso ao final do cadastro
    private By mensagemContaCriada = By.cssSelector("[data-qa='account-created']");

    // Ações
    public void preencherDadosDaConta(String senha) {
        driver.findElement(radioGeneroMr).click();
        driver.findElement(campoSenha).sendKeys(senha);
    }

    public void preencherEnderecoCompleto(String pNome, String uNome, String end, String estado, String cidade, String cep, String celular) {
        driver.findElement(campoPrimeiroNome).sendKeys(pNome);
        driver.findElement(campoUltimoNome).sendKeys(uNome);
        driver.findElement(campoEndereco).sendKeys(end);
        driver.findElement(campoEstado).sendKeys(estado);
        driver.findElement(campoCidade).sendKeys(cidade);
        driver.findElement(campoCep).sendKeys(cep);
        driver.findElement(campoCelular).sendKeys(celular);
    }

    public void clicarBotaoCriarConta() {
        driver.findElement(botaoCriarConta).click();
    }

    public String obterTextoContaCriada() {
        return driver.findElement(mensagemContaCriada).getText();
    }
}