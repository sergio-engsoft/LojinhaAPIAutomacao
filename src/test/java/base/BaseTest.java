package base;

import config.ConfiguracaoManager;
import datafactory.UsuarioDataFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

@Epic("Lojinha API")
public abstract class BaseTest {

    protected static String token;

    @BeforeAll
    static void configurarAmbiente() {
        var config = ConfiguracaoManager.getConfiguracao();

        RestAssured.baseURI            = config.baseUri();
        RestAssured.basePath           = config.basePath();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.filters(
                new AllureRestAssured(),
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }

    protected static void autenticar() {
        token = given()
                .body(UsuarioDataFactory.criarUsuarioAdministrador())
        .when()
                .post("/v2/login")
        .then()
                .statusCode(200)
                .extract()
                .path("data.token");
    }

    protected static void limparDadosDoUsuario() {
        given()
                .header("token", token)
        .when()
                .delete("/v2/dados")
        .then()
                .statusCode(204);
    }
}
