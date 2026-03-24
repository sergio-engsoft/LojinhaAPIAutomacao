package modulos.usuario;

import base.BaseTest;
import datafactory.UsuarioDataFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pojo.UsuarioPojo;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Usuário")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Tag("regression")
class UsuarioTest extends BaseTest {

    @BeforeAll
    static void setup() {
        autenticar();
    }

    // Login

    @Test
    @Tag("smoke")
    @Story("Login")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Login com credenciais válidas retorna token e status 200")
    void loginComCredenciaisValidasRetornaToken() {
        given()
                .body(UsuarioDataFactory.criarUsuarioAdministrador())
        .when()
                .post("/v2/login")
        .then()
                .statusCode(200)
                .body("message", equalTo("Sucesso ao realizar o login"))
                .body("data.token", notNullValue())
                .body(matchesJsonSchemaInClasspath("schemas/login-sucesso-schema.json"));
    }

    @Test
    @Story("Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login com senha incorreta retorna 401")
    void loginComSenhaIncorretaRetorna401() {
        given()
                .body(UsuarioDataFactory.criarPayloadLogin("admin", "senha_errada_intencional"))
        .when()
                .post("/v2/login")
        .then()
                .statusCode(401);
    }

    @Test
    @Story("Login")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login com usuário inexistente retorna 401")
    void loginComUsuarioInexistenteRetorna401() {
        given()
                .body(UsuarioDataFactory.criarPayloadLogin("usuario_inexistente", "senha123"))
        .when()
                .post("/v2/login")
        .then()
                .statusCode(401);
    }

    // Criação

    static Stream<Arguments> camposObrigatorios() {
        String erro = "usuarioNome, usuarioLogin e usuarioSenha são atributos obrigatórios";
        return Stream.of(
                Arguments.of("sem nome",  UsuarioDataFactory.criarUsuarioSemNome(),  erro),
                Arguments.of("sem senha", UsuarioDataFactory.criarUsuarioSemSenha(), erro),
                Arguments.of("sem login", UsuarioDataFactory.criarUsuarioSemLogin(), erro)
        );
    }

    @ParameterizedTest(name = "Criar usuário {0} retorna 400")
    @MethodSource("camposObrigatorios")
    @Story("Criação")
    @Severity(SeverityLevel.NORMAL)
    void criarUsuarioComCampoAusenteRetorna400(String desc, UsuarioPojo payload, String erro) {
        given()
                .body(payload)
        .when()
                .post("/v2/usuarios")
        .then()
                .statusCode(400)
                .body("error", equalTo(erro));
    }

    @Test
    @Story("Criação")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Criar usuário já existente retorna 409")
    void criarUsuarioJaExistenteRetorna409() {
        given()
                .header("token", token)
                .body(UsuarioDataFactory.criarUsuarioAdministrador())
        .when()
                .post("/v2/usuarios")
        .then()
                .statusCode(409);
    }

    // Remoção de dados

    @Test
    @Story("Remoção de Dados")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Remover dados sem token retorna 401")
    void removerDadosSemTokenRetorna401() {
        given()
        .when()
                .delete("/v2/dados")
        .then()
                .statusCode(401);
    }
}
