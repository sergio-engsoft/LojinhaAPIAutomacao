package modulos.componente;

import base.BaseTest;
import datafactory.ComponenteDataFactory;
import datafactory.ProdutoDataFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pojo.ComponentePojo;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Componente")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Tag("regression")
class ComponenteTest extends BaseTest {

    private static int produtoId;
    private static int componenteId;

    @BeforeAll
    static void setup() {
        autenticar();
        produtoId    = criarProduto();
        componenteId = criarComponente(produtoId);
    }

    @AfterAll
    static void teardown() {
        given()
                .header("token", token)
        .when()
                .delete("/v2/produtos/" + produtoId);

        limparDadosDoUsuario();
    }

    // POST /v2/produtos/{id}/componentes

    @Test
    @Tag("smoke")
    @Story("Adição")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Adicionar componente válido retorna 201 com schema correto")
    void adicionarComponenteValidoRetorna201() {
        int id = given()
                .header("token", token)
                .body(ComponenteDataFactory.criarComponenteValido())
        .when()
                .post(componentesUrl())
        .then()
                .statusCode(201)
                .body("message", equalTo("Componente de produto adicionado com sucesso"))
                .body("data.componenteId", notNullValue())
                .body(matchesJsonSchemaInClasspath("schemas/componente-criado-schema.json"))
                .extract()
                .path("data.componenteId");

        deletarComponente(id);
    }

    static Stream<Arguments> camposObrigatorios() {
        String erro = "componenteNome e componenteQuantidade são atributos obrigatórios";
        return Stream.of(
                Arguments.of("sem nome",       ComponenteDataFactory.criarComponenteSemNome(),       erro),
                Arguments.of("sem quantidade", ComponenteDataFactory.criarComponenteSemQuantidade(), erro)
        );
    }

    @ParameterizedTest(name = "Adicionar componente {0} retorna 400")
    @MethodSource("camposObrigatorios")
    @Story("Adição")
    @Severity(SeverityLevel.NORMAL)
    void adicionarComponenteComCampoAusenteRetorna400(String desc, ComponentePojo payload, String erro) {
        given()
                .header("token", token)
                .body(payload)
        .when()
                .post(componentesUrl())
        .then()
                .statusCode(400)
                .body("error", equalTo(erro));
    }

    @Test
    @Story("Adição")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Adicionar componente sem token retorna 401")
    void adicionarComponenteSemTokenRetorna401() {
        given()
                .body(ComponenteDataFactory.criarComponenteValido())
        .when()
                .post(componentesUrl())
        .then()
                .statusCode(401);
    }

    @Test
    @Story("Adição")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Adicionar componente a produto inexistente retorna 404")
    void adicionarComponenteEmProdutoInexistenteRetorna404() {
        given()
                .header("token", token)
                .body(ComponenteDataFactory.criarComponenteValido())
        .when()
                .post("/v2/produtos/999999/componentes")
        .then()
                .statusCode(404);
    }

    @Test
    @Story("Adição")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Adicionar componente com quantidade zero retorna 422")
    void adicionarComponenteComQuantidadeZeroRetorna422() {
        given()
                .header("token", token)
                .body(ComponenteDataFactory.criarComponenteComQuantidadeAbaixoDoMinimo())
        .when()
                .post(componentesUrl())
        .then()
                .statusCode(422)
                .body("error", equalTo("A quantidade mínima para o componente não deve ser inferior a 1"));
    }

    // GET /v2/produtos/{id}/componentes

    @Test
    @Tag("smoke")
    @Story("Busca")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Listar componentes retorna 200")
    void listarComponentesRetorna200() {
        given()
                .header("token", token)
        .when()
                .get(componentesUrl())
        .then()
                .statusCode(200)
                .body("message", equalTo("Listagem de componentes de produto realizada com sucesso"));
    }

    @Test
    @Story("Busca")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Listar componentes sem token retorna 401")
    void listarComponentesSemTokenRetorna401() {
        given()
        .when()
                .get(componentesUrl())
        .then()
                .statusCode(401);
    }

    // PUT /v2/produtos/{id}/componentes/{id}

    @Test
    @Story("Atualização")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Atualizar componente com dados válidos retorna 200 e persiste")
    void atualizarComponenteRetorna200EPersiste() {
        given()
                .header("token", token)
                .body(ComponenteDataFactory.criarComponenteComNome("Componente Atualizado"))
        .when()
                .put(componenteUrl(componenteId))
        .then()
                .statusCode(200)
                .body("message", equalTo("Componente de produto alterado com sucesso"));

        given()
                .header("token", token)
        .when()
                .get(componenteUrl(componenteId))
        .then()
                .statusCode(200)
                .body("data.componenteNome", equalTo("Componente Atualizado"));
    }

    @ParameterizedTest(name = "Atualizar componente {0} retorna 400")
    @MethodSource("camposObrigatorios")
    @Story("Atualização")
    @Severity(SeverityLevel.NORMAL)
    void atualizarComponenteComCampoAusenteRetorna400(String desc, ComponentePojo payload, String erro) {
        given()
                .header("token", token)
                .body(payload)
        .when()
                .put(componenteUrl(componenteId))
        .then()
                .statusCode(400)
                .body("error", equalTo(erro));
    }

    @Test
    @Story("Atualização")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Atualizar componente sem token retorna 401")
    void atualizarComponenteSemTokenRetorna401() {
        given()
                .body(ComponenteDataFactory.criarComponenteValido())
        .when()
                .put(componenteUrl(componenteId))
        .then()
                .statusCode(401);
    }

    @Test
    @Story("Atualização")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Atualizar componente inexistente retorna 404")
    void atualizarComponenteInexistenteRetorna404() {
        given()
                .header("token", token)
                .body(ComponenteDataFactory.criarComponenteValido())
        .when()
                .put(componenteUrl(99999999))
        .then()
                .statusCode(404);
    }

    // DELETE /v2/produtos/{id}/componentes/{id}

    @Test
    @Story("Remoção")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Remover componente retorna 204 e torna-o inacessível")
    void removerComponenteRetorna204ETornaInacessivel() {
        int id = criarComponente(produtoId);

        given()
                .header("token", token)
        .when()
                .delete(componenteUrl(id))
        .then()
                .statusCode(204);

        given()
                .header("token", token)
        .when()
                .get(componenteUrl(id))
        .then()
                .statusCode(404);
    }

    @Test
    @Story("Remoção")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Remover componente sem token retorna 401")
    void removerComponenteSemTokenRetorna401() {
        given()
        .when()
                .delete(componenteUrl(componenteId))
        .then()
                .statusCode(401);
    }

    @Test
    @Story("Remoção")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Remover componente inexistente retorna 404")
    void removerComponenteInexistenteRetorna404() {
        given()
                .header("token", token)
        .when()
                .delete(componenteUrl(999999999))
        .then()
                .statusCode(404);
    }

    // Helpers

    private static int criarProduto() {
        return given()
                .header("token", token)
                .body(ProdutoDataFactory.criarProdutoValido())
        .when()
                .post("/v2/produtos")
        .then()
                .statusCode(201)
                .extract()
                .path("data.produtoId");
    }

    private static int criarComponente(int produtoId) {
        return given()
                .header("token", token)
                .body(ComponenteDataFactory.criarComponenteValido())
        .when()
                .post("/v2/produtos/" + produtoId + "/componentes")
        .then()
                .statusCode(201)
                .extract()
                .path("data.componenteId");
    }

    private void deletarComponente(int id) {
        given()
                .header("token", token)
        .when()
                .delete(componenteUrl(id))
        .then()
                .statusCode(204);
    }

    private String componentesUrl() {
        return "/v2/produtos/" + produtoId + "/componentes";
    }

    private String componenteUrl(int id) {
        return componentesUrl() + "/" + id;
    }
}
