package tests;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestAssuredApiTest {

    @Test
    public void deveConsultarUsuarioComSucesso() {
        // Realiza um GET em uma API pública e valida o Status Code e o corpo da resposta
        given()
            .baseUri("https://reqres.in/api")
        .when()
            .get("/users/2")
        .then()
            .statusCode(200)
            .body("data.id", equalTo(2))
            .body("data.first_name", equalTo("Janet"));
    }
}