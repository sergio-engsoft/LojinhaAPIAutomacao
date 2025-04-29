package modulos.produto;
import datafactory.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import pojo.ProdutoPojo;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do modulo de Produto")
public class ProdutoTest {

private static String token;
private static int produtoId;

    @BeforeAll
    public static void beforeAll() {
        baseURI = "http://165.227.93.41";
        basePath = "/lojinha";

        token = given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarUsuarioAdministrador())
            .when()
                .post("/v2/login")
            .then()
                .extract()
                .path("data.token");


        produtoId = given().contentType(ContentType.JSON)
                .header("token", token)
                .body(ProdutoDataFactory.criarProdutoComum())
            .when()
                .post("/v2/produtos")
            .then()
                .extract()
                .path("data.produtoId");
    }

    @Test
    @DisplayName("Validar os limites minimos valor do produto")
    public void testValidarValorProdutoAbaixoLimite() {
        ProdutoPojo produtoValorAbaixo = ProdutoDataFactory.criarProdutoComum();
        produtoValorAbaixo.setProdutoValor(0.00);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(produtoValorAbaixo)
            .when()
                .post("/v2/produtos")
            .then()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }

    @Test
    @DisplayName("Validar os limites maximos do valor do Produto")
    public void testValidarLimitesAcimaDoLimite() {
        ProdutoPojo produtoValorAlto = ProdutoDataFactory.criarProdutoComum();
        produtoValorAlto.setProdutoValor(7001.00);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(produtoValorAlto)
            .when()
                .post("/v2/produtos")
            .then()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }

    @Test
    @DisplayName("Validar a criação do Produto")
    public void testCriarProduto() {
        ProdutoPojo produtoValidoTest = ProdutoDataFactory.criarProdutoComum();
       int produtoValidoId = given().contentType(ContentType.JSON)
                .header("token", token)
                .body(produtoValidoTest)
            .when()
                .post("/v2/produtos")
            .then()
                .statusCode(201)
                .body("data.produtoNome", equalTo(produtoValidoTest.getProdutoNome()))
                .body("data.produtoValor", equalTo((float) produtoValidoTest.getProdutoValor().doubleValue()))
                .extract()
                .path("data.produtoId");
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .delete("/v2/produtos/" + produtoValidoId)
            .then()
                .statusCode(204);
    }


    @Test
    @DisplayName("Validar criar um produto com usuario não autorizado")
    public void testValidarUsuarioNaoAutorizadoCriarProduto() {
        given().contentType(ContentType.JSON)
                .body(ProdutoDataFactory.criarProdutoComum())
            .when()
                .post("/v2/produtos")
            .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Validar o erro ao cadastrar um produto sem inserir nome")
    public void testValidarCamposObrigatorioNome() {
        ProdutoPojo produtoSemNome = ProdutoDataFactory.criarProdutoComum();
        produtoSemNome.setProdutoNome(null);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(produtoSemNome)
            .when()
                .post("/v2/produtos")
            .then()
                .statusCode(400)
                .body("error", equalTo("produtoNome, produtoValor e produtoCores são campos obrigatórios"));
    }

    @Test
    @DisplayName("Validar o erro ao cadastrar um produto sem inserir valor")
    public void testValidarCamposObrigatorioValor() {
        ProdutoPojo produtoSemValor = ProdutoDataFactory.criarProdutoComum();
        produtoSemValor.setProdutoValor(null);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(produtoSemValor)
            .when()
                .post("/v2/produtos")
            .then()
                .statusCode(400)
                .body("error", equalTo("produtoNome, produtoValor e produtoCores são campos obrigatórios"));
    }

    @Test
    @DisplayName("Validar o erro ao cadastrar um produto sem inserir cores")
    public void testValidarCamposObrigatorioCores() {
        ProdutoPojo produtoSemCores = ProdutoDataFactory.criarProdutoComum();
        produtoSemCores.setProdutoCores(null);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(produtoSemCores)
            .when()
                .post("/v2/produtos")
            .then()
                .statusCode(400)
                .body("error", equalTo("produtoNome, produtoValor e produtoCores são campos obrigatórios"));
    }

    @Test
    @DisplayName("Validar a busca por produtos")
    public void testValidarBuscarTodosOsProdutos() {
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .get("/v2/produtos")
            .then()
                .statusCode(200)
                .body("message", equalTo("Listagem de produtos realizada com sucesso"));
    }

    @Test
    @DisplayName("Validar a busca por produtos não autorizada")
    public void testValidarBuscarProdutosSemToken() {
        given().contentType(ContentType.JSON)
            .when()
                .get("/v2/produtos")
            .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Validar a busca por um produto")
    public void testValidarBuscarUmProduto() {
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .get("/v2/produtos/" + produtoId)
            .then()
                .statusCode(200)
                .body("data.produtoId", equalTo(produtoId));
    }

    @Test
    @DisplayName("Validar a busca por um produtos sem autorização")
    public void testValidarBuscarUmProdutoSemToken() {
        given().contentType(ContentType.JSON)
            .when()
                .get("/v2/produtos/" + produtoId)
            .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Validar a busca por um produtos inexistente")
    public void testValidarBuscarUmProdutoInexistente() {
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .get("/v2/produtos/9999999")
            .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Validar a alteração de um produto")
    public void testValidarAlteracaoDoProduto() {
        ProdutoPojo produtoAtualizado = ProdutoDataFactory.criarProdutoComum();
        produtoAtualizado.setProdutoNome("Teste produto atualizado");
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(produtoAtualizado)
            .when()
                .put("/v2/produtos/" + produtoId)
            .then()
                .statusCode(200)
                .body("message", equalTo("Produto alterado com sucesso"));
    }

    @Test
    @DisplayName("Validar a alteração de um produto sem preencher campo obrigatorio")
    public void testValidarAlteracaoDoProdutoCampoObrigatorio() {
        ProdutoPojo produtoAtualizado = ProdutoDataFactory.criarProdutoComum();
        produtoAtualizado.setProdutoNome(null);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(produtoAtualizado)
            .when()
                .put("/v2/produtos/" + produtoId)
            .then()
                .statusCode(400)
                .body("error", equalTo("produtoNome, produtoValor e produtoCores são campos obrigatórios"));
    }

    @Test
    @DisplayName("Validar a alteração de um produto sem token")
    public void testValidarAlteracaoDoProdutoSemToken() {
        ProdutoPojo produtoAtualizado = ProdutoDataFactory.criarProdutoComum();
        produtoAtualizado.setProdutoNome("Teste produto atualizado");
        given().contentType(ContentType.JSON)
                .body(produtoAtualizado)
            .when()
                .put("/v2/produtos/" + produtoId)
            .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Validar a alteração de um produto inexistente")
    public void testValidarAlteracaoDoProdutoInexistente() {
        ProdutoPojo produtoAtualizado = ProdutoDataFactory.criarProdutoComum();
        produtoAtualizado.setProdutoNome("Teste produto atualizado");
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(produtoAtualizado)
            .when()
                .put("/v2/produtos/9999999")
            .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Validar a alteração de um produto para valor fora dos limites")
    public void testValidarAlteracaoDoProdutoForaDosLimites() {
        ProdutoPojo produtoAtualizado = ProdutoDataFactory.criarProdutoComum();
        produtoAtualizado.setProdutoValor(7001.00);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(produtoAtualizado)
            .when()
                .put("/v2/produtos/" + produtoId)
            .then()
                .statusCode(422);
    }
    @Test
    @DisplayName("Validar a remoção de um produto sem token")
    public void testValidarRemocaoDeUmProdutoSemToken() {
        given().contentType(ContentType.JSON)
                .when()
                .delete("/v2/produtos/" + produtoId)
                .then()
                .statusCode(401);
    }
        @Test
        @DisplayName("Validar a remoção de um produto")
        public void testValidarRemocaoDeUmProduto() {

           int produtoIdTestRemocao = given().contentType(ContentType.JSON)
                    .header("token", token)
                    .body(ProdutoDataFactory.criarProdutoComum())
                    .when()
                    .post("/v2/produtos")
                    .then()
                    .extract()
                    .path("data.produtoId");

            given().contentType(ContentType.JSON)
                    .header("token", token)
                    .when()
                    .delete("/v2/produtos/" + produtoIdTestRemocao)
                    .then()
                    .statusCode(204);
    }
    @AfterAll
    public static void afterAll() {
        given().contentType(ContentType.JSON)
                .header("token", token)
                .when()
                .delete("/v2/produtos/" + produtoId)
                .then()
                .statusCode(204);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .when()
                .delete("/v2/dados")
                .then()
                .statusCode(204);
        }
    }

