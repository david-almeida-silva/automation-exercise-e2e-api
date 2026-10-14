package tests;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class AutomationExerciseApiTest {

    @BeforeAll
    public static void setUp() {
        baseURI = "https://automationexercise.com/api";
    }

    @Test
    public void deveRetornarListaDeProdutosComSucesso() {
        // 1. Fazemos a requisição e guardamos a resposta inteira como uma String (Texto)
        String responseHtml = given()
            .when()
            .get("/productsList")
            .then()
            .statusCode(200) // Valida se o servidor respondeu OK
            .extract().asString();

        // 2. Usamos Regex para arrancar fora as tags <html> e <body> que a API manda errado
        String jsonLimpo = responseHtml.replaceAll("<[^>]*>", "").trim();
        
        // 3. Forçamos o REST Assured a ler esse texto limpo como se fosse um JSON
        JsonPath jsonPath = new JsonPath(jsonLimpo);

        // 4. Fazemos as validações de regra de negócio
        assertEquals(200, jsonPath.getInt("responseCode"), "O status code dentro do JSON falhou!");
        assertFalse(jsonPath.getList("products").isEmpty(), "A lista de produtos veio vazia!");
    }

    @Test
    public void devePesquisarProdutoPorNomeComSucesso() {
        String responseHtml = given()
            .formParam("search_product", "top")
        .when()
            .post("/searchProduct")
        .then()
            .statusCode(200)
            .extract().asString();

        // Limpeza do HTML sujo
        String jsonLimpo = responseHtml.replaceAll("<[^>]*>", "").trim();
        JsonPath jsonPath = new JsonPath(jsonLimpo);

        assertEquals(200, jsonPath.getInt("responseCode"));
        assertFalse(jsonPath.getList("products").isEmpty(), "A busca não encontrou itens!");
        
        // Validação extra: O primeiro produto retornado realmente tem a palavra "Top" no nome?
        String nomePrimeiroProduto = jsonPath.getString("products[0].name");
        assertTrue(nomePrimeiroProduto.toLowerCase().contains("top"), "O produto retornado não condiz com a busca!");
    }
}