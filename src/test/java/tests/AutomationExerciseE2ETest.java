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
    private DeleteAccountPage deleteAccountPage; // 1. Nova página declarada

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        // MODO INVISÍVEL ATIVADO: Essencial para rodar no GitHub Actions
        options.addArguments("--headless=new");
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        paymentPage = new PaymentPage(driver);
        deleteAccountPage = new DeleteAccountPage(driver); // 2. Nova página instanciada
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
        // 1. GERA USUÁRIO E FAZ CADASTRO
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

        // 4. VALIDAÇÃO DA COMPRA
        assertEquals("ORDER PLACED!", paymentPage.obterTextoPedidoConfirmado(), "A compra não foi finalizada!");

        // 5. TEARDOWN: LIMPEZA DE DADOS (NOVO)
        deleteAccountPage.clicarMenuDeleteAccount();
        assertEquals("ACCOUNT DELETED!", deleteAccountPage.obterTextoContaExcluida(), "Falha ao limpar o banco de dados: a conta não foi excluída!");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}