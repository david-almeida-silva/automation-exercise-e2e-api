package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*; // Importa todas as páginas

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutomationExerciseE2ETest {

    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;
    private DeleteAccountPage deleteAccountPage; 

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        
        /* 
         * SETUP DE QA SÊNIOR PARA CI/CD:
         * Estas opções garantem que o teste rode de forma estável tanto na máquina 
         * local quanto em instâncias Linux sem interface gráfica (GitHub Actions/Docker).
         */
        options.addArguments("--headless=new"); // Executa sem abrir a interface gráfica (obrigatório para servidores nuvem)
        options.addArguments("--no-sandbox"); // Contorna restrições de segurança do SO Linux que impedem o Chrome de iniciar
        options.addArguments("--disable-dev-shm-usage"); // Evita estouro de memória (Out of Memory) no contêiner do servidor
        options.addArguments("--window-size=1920,1080"); // Força a resolução Full HD para garantir que elementos responsivos não fiquem ocultos
        
        driver = new ChromeDriver(options);
        
        // Espera implícita global para carregamento de elementos no DOM
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Inicialização das páginas (Page Factory / POM)
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        paymentPage = new PaymentPage(driver);
        deleteAccountPage = new DeleteAccountPage(driver); 
    }

    @Test
    public void deveAcessarTelaDeLoginComSucesso() {
        homePage.acessarPaginaInicial();
        homePage.clicarSignupLogin();
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void deveExibirErroAoLogarComCredenciaisInvalidas() {
        homePage.acessarPaginaInicial();
        homePage.clicarSignupLogin();
        loginPage.preencherLogin("email_invalido@teste.com", "senhaerrada123");
        loginPage.clicarBotaoLogin();
        assertEquals("Your email or password is incorrect!", loginPage.obterTextoDoErro());
    }

    @Test
    public void deveRealizarCheckoutCompletoComSucesso() {
        // 1. GERA USUÁRIO E FAZ CADASTRO (Massa de dados dinâmica)
        String emailDinamico = "david" + System.currentTimeMillis() + "@qa.com";
        homePage.acessarPaginaInicial();
        homePage.clicarSignupLogin();
        loginPage.preencherCadastroInicial("David QA", emailDinamico);
        loginPage.clicarBotaoSignup();
        signupPage.preencherDadosDaConta("SenhaForte123");
        signupPage.preencherEnderecoCompleto("David", "Silva", "Rua QA, 10", "SP", "Osasco", "06000-000", "11999999999");
        signupPage.clicarBotaoCriarConta();

        // 2. ADICIONA PRODUTO AO CARRINHO
        productsPage.acessarPaginaDeProdutos();
        productsPage.adicionarPrimeiroProdutoAoCarrinho();
        productsPage.clicarNoModalViewCart();

        // 3. FLUXO DE CHECKOUT E PAGAMENTO
        cartPage.clicarProceedToCheckout();
        checkoutPage.inserirComentario("Compra automatizada para portfólio!");
        checkoutPage.clicarPlaceOrder();
        paymentPage.preencherDadosPagamento("David QA", "4111111111111111", "123", "12", "2030");
        paymentPage.clicarPagarEConfirmar();

        // 4. VALIDAÇÃO DA COMPRA (Garantia da Regra de Negócio)
        assertEquals("ORDER PLACED!", paymentPage.obterTextoPedidoConfirmado(), "A compra não foi finalizada!");

        // 5. TEARDOWN: LIMPEZA DE DADOS (Impede acúmulo de lixo no banco de dados)
        deleteAccountPage.clicarMenuDeleteAccount();
        assertEquals("ACCOUNT DELETED!", deleteAccountPage.obterTextoContaExcluida(), "Falha ao limpar o banco de dados: a conta não foi excluída!");
    }

    @AfterEach
    public void tearDown() {
        // Encerra o driver e mata o processo do Chrome na memória
        if (driver != null) {
            driver.quit();
        }
    }
}