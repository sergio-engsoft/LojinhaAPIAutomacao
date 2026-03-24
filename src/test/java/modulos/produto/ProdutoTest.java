package modulos.produto;

import base.BaseTest;
import datafactory.ProdutoDataFactory;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pojo.ProdutoPojo;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@Feature("Produto")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Tag("regression")
class ProdutoTest extends BaseTest {

    private static int produtoId;

    @BeforeAll
    static void setup() {
        autenticar();
        produtoId = criarProduto();
    }

    @AfterAll
    static void teardown() {
        deletarProduto(produtoId);
        limparDadosDoUsuario();
    }

    // POST /v2/produtos

    @Test
    @Tag("smoke")
    @Story("Criação")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Criar produto válido retorna 201 com schema correto")
    void criarProdutoValidoRetorna201() {
        ProdutoPojo produto = ProdutoDataFactory.criarProdutoValido();

        int id = given()
                .header("token", token)
                .body(produto)
        .when()
                .post("/v2/produtos")
        .then()
                .statusCode(201)
                .body("data.produtoNome", equalTo(produto.getProdutoNome()))
                .body("data.produtoValor", equalTo(produto.getProdutoValor().floatValue()))
                .body("data.produtoId", notNullValue())
                .body(matchesJsonSchemaInClasspath("schemas/produto-criado-schema.json"))
                .extract()
                .path("data.produtoId");

        deletarProduto(id);
    }

    @Test
    @Story("Criação")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Criar produto sem token retorna 401")
    void criarProdutoSemTokenRetorna401() {
        given()
                .body(ProdutoDataFactory.criarProdutoValido())
        .when()
                .post("/v2/produtos")
        .then()
                .statusCode(401);
    }

    static Stream<Arguments> valoresInvalidos() {
        String erro = "O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00";
        return Stream.of(
                Arguments.of("valor zero",      ProdutoDataFactory.criarProdutoComValorAbaixoDoMinimo(), erro),
                Arguments.of("valor R$7001,00", ProdutoDataFactory.criarProdutoComValorAcimaDoMaximo(),  erro)
        );
    }

    @ParameterizedTest(name = "Criar produto com {0} retorna 422")
    @MethodSource("valoresInvalidos")
    @Story("Criação")
    @Severity(SeverityLevel.NORMAL)
    void criarProdutoComValorForaDosLimitesRetorna422(String desc, ProdutoPojo payload, String erro) {
        given()
                .header("token", token)
                .body(payload)
        .when()
                .post("/v2/produtos")
        .then()
                .statusCode(422)
                .body("error", equalTo(erro));
    }

    static Stream<Arguments> camposObrigatorios() {
        String erro = "produtoNome, produtoValor e produtoCores são campos obrigatórios";
        return Stream.of(
                Arguments.of("sem nome",  ProdutoDataFactory.criarProdutoSemNome(),  erro),
                Arguments.of("sem valor", ProdutoDataFactory.criarProdutoSemValor(), erro),
                Arguments.of("sem cores", ProdutoDataFactory.criarProdutoSemCores(), erro)
        );
    }

    @ParameterizedTest(name = "Criar produto {0} retorna 400")
    @MethodSource("camposObrigatorios")
    @Story("Criação")
    @Severity(SeverityLevel.NORMAL)
    void criarProdutoComCampoAusenteRetorna400(String desc, ProdutoPojo payload, String erro) {
        given()
                .header("token", token)
                .body(payload)
        .when()
                .post("/v2/produtos")
        .then()
                .statusCode(400)
                .body("error", equalTo(erro));
    }

    // GET /v2/produtos

    @Test
    @Tag("smoke")
    @Story("Busca")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Listar produtos retorna 200")
    void listarProdutosRetorna200() {
        given()
                .header("token", token)
        .when()
                .get("/v2/produtos")
        .then()
                .statusCode(200)
                .body("message", equalTo("Listagem de produtos realizada com sucesso"));
    }

    @Test
    @Story("Busca")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Listar produtos sem token retorna 401")
    void listarProdutosSemTokenRetorna401() {
        given()
        .when()
                .get("/v2/produtos")
        .then()
                .statusCode(401);
    }

    // GET /v2/produtos/{id}

    @Test
    @Story("Busca")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Buscar produto por ID retorna 200")
    void buscarProdutoPorIdRetorna200() {
        given()
                .header("token", token)
        .when()
                .get("/v2/produtos/" + produtoId)
        .then()
                .statusCode(200)
                .body("data.produtoId", equalTo(produtoId));
    }

    @Test
    @Story("Busca")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Buscar produto por ID sem token retorna 401")
    void buscarProdutoPorIdSemTokenRetorna401() {
        given()
        .when()
                .get("/v2/produtos/" + produtoId)
        .then()
                .statusCode(401);
    }

    @Test
    @Story("Busca")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Buscar produto inexistente retorna 404")
    void buscarProdutoInexistenteRetorna404() {
        given()
                .header("token", token)
        .when()
                .get("/v2/produtos/9999999")
        .then()
                .statusCode(404);
    }

    // PUT /v2/produtos/{id}

    @Test
    @Story("Atualização")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Atualizar produto com dados válidos retorna 200")
    void atualizarProdutoRetorna200() {
        ProdutoPojo atualizado = ProdutoDataFactory.criarProdutoValido();
        atualizado.setProdutoNome("Produto Atualizado");

        given()
                .header("token", token)
                .body(atualizado)
        .when()
                .put("/v2/produtos/" + produtoId)
        .then()
                .statusCode(200)
                .body("message", equalTo("Produto alterado com sucesso"));
    }

    @ParameterizedTest(name = "Atualizar produto {0} retorna 400")
    @MethodSource("camposObrigatorios")
    @Story("Atualização")
    @Severity(SeverityLevel.NORMAL)
    void atualizarProdutoComCampoAusenteRetorna400(String desc, ProdutoPojo payload, String erro) {
        given()
                .header("token", token)
                .body(payload)
        .when()
                .put("/v2/produtos/" + produtoId)
        .then()
                .statusCode(400)
                .body("error", equalTo(erro));
    }

    @Test
    @Story("Atualização")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Atualizar produto sem token retorna 401")
    void atualizarProdutoSemTokenRetorna401() {
        given()
                .body(ProdutoDataFactory.criarProdutoValido())
        .when()
                .put("/v2/produtos/" + produtoId)
        .then()
                .statusCode(401);
    }

    @Test
    @Story("Atualização")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Atualizar produto inexistente retorna 404")
    void atualizarProdutoInexistenteRetorna404() {
        given()
                .header("token", token)
                .body(ProdutoDataFactory.criarProdutoValido())
        .when()
                .put("/v2/produtos/9999999")
        .then()
                .statusCode(404);
    }

    @Test
    @Story("Atualização")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Atualizar produto com valor acima do máximo retorna 422")
    void atualizarProdutoComValorAcimaDoMaximoRetorna422() {
        given()
                .header("token", token)
                .body(ProdutoDataFactory.criarProdutoComValorAcimaDoMaximo())
        .when()
                .put("/v2/produtos/" + produtoId)
        .then()
                .statusCode(422);
    }

    // DELETE /v2/produtos/{id}

    @Test
    @Story("Remoção")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Remover produto retorna 204")
    void removerProdutoRetorna204() {
        int id = criarProduto();

        given()
                .header("token", token)
        .when()
                .delete("/v2/produtos/" + id)
        .then()
                .statusCode(204);
    }

    @Test
    @Story("Remoção")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Remover produto sem token retorna 401")
    void removerProdutoSemTokenRetorna401() {
        given()
        .when()
                .delete("/v2/produtos/" + produtoId)
        .then()
                .statusCode(401);
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

    private static void deletarProduto(int id) {
        given()
                .header("token", token)
        .when()
                .delete("/v2/produtos/" + id);
    }
}
