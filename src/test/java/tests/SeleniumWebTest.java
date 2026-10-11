package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumWebTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Configura o Chrome para rodar em modo invisível (Headless)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); 
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        // O Selenium 4 já baixa e gerencia o ChromeDriver automaticamente
        driver = new ChromeDriver(options);
    }

    @Test
    public void deveValidarTituloDaPagina() {
        // Navega até o Swag Labs (a mesma aplicação do seu projeto Playwright)
        driver.get("https://www.saucedemo.com/");

        // Captura o título da aba do navegador
        String titulo = driver.getTitle();

        // Valida se o título é o esperado
        assertEquals("Swag Labs", titulo, "O título da página está incorreto!");
    }

    @AfterEach
    public void tearDown() {
        // Fecha o navegador após cada teste para não consumir memória
        if (driver != null) {
            driver.quit();
        }
    }
}