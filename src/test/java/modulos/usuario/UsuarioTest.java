package modulos.usuario;
import datafactory.UsuarioDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.*;

@DisplayName("Testes de API Rest do modulo de Usuário")
public class UsuarioTest {
private static String token;

    @BeforeAll
    public static void beforeAll() {
        baseURI = "http://165.227.93.41";
        basePath = "/lojinha";
    }

    private String obterToken() {
        return given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarusuario("admin", "admin", null))
            .when()
                .post("/v2/login")
            .then()
                .statusCode(200)
                .extract()
                .path("data.token");
    }

    @Test
    @DisplayName("Validar obtenção de token do usuario")
    public void validarObtencaoDeToken() {
        given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarUsuarioAdministrador())
            .when()
                .post("/v2/login")
            .then()
                .statusCode(200)
                .body("message", equalTo("Sucesso ao realizar o login"));
    }
    @Test
    @DisplayName("Validar obtenção de token do usuario sem autorizacao")
    public void validarObtencaoDeTokenSemAutorizacao() {
        given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarusuario("admin", "errada", null))
            .when()
                .post("/v2/login")
            .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Validar criação de usuario Ja Existente")
    public void validarCriacaoDeUsuarioJaExistente() {
    token = obterToken();
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(UsuarioDataFactory.criarusuario("admin", "admin", "admin"))
            .when()
                .post("/v2/usuarios")
            .then()
                .statusCode(409);
    }
    @Test
    @DisplayName("Validar criação de usuario Sem Nome")
    public void validarCriacaoDeUsuarioSemNome() {
        given().contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarusuario("admin", "admin", null))
            .when()
                .post("/v2/usuarios")
            .then()
                .statusCode(400)
                .body("error", equalTo("usuarioNome, usuarioLogin e usuarioSenha são atributos obrigatórios"));
    }
    @Test
    @DisplayName("Validar criação de usuario Sem Senha")
    public void validarCriacaoDeUsuarioSemSenha() {
        given().contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarusuario("admin", null, "admin"))
            .when()
                .post("/v2/usuarios")
            .then()
                .statusCode(400)
                .body("error", equalTo("usuarioNome, usuarioLogin e usuarioSenha são atributos obrigatórios"));
    }
    @Test
    @DisplayName("Validar criação de usuario Sem Login")
    public void validarCriacaoDeUsuarioSemLogin() {
        given().contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarusuario(null, "admin", "admin"))
            .when()
                .post("/v2/usuarios")
            .then()
                .statusCode(400)
                .body("error", equalTo("usuarioNome, usuarioLogin e usuarioSenha são atributos obrigatórios"));
    }
    @Test
    @DisplayName("Validar Remocao Dados do Usuario")
    public void validarRemocaoDadosDoUsuario() {
        token = obterToken();
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .delete("/v2/dados")
            .then()
                .statusCode(204);
    }
    @Test
    @DisplayName("Validar Remocao de Dados do Usuario sem Autorização")
    public void validarRemocaoDadosUsuarioSemAutorizacao() {
        given().contentType(ContentType.JSON)
            .when()
                .delete("/v2/dados")
            .then()
                .statusCode(401);
    }
}

