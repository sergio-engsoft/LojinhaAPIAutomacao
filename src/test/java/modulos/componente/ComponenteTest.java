package modulos.componente;
import datafactory.ComponenteDataFactory;
import datafactory.ProdutoDataFactory;
import datafactory.UsuarioDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ComponentePojo;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;

@DisplayName("Testes de API Rest do modulo de Componente")
public class ComponenteTest {
    private static String token;
    private static int produtoId;
    private static int componenteId;

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

        componenteId = given().contentType(ContentType.JSON)
                .header("token", token)
                .body(ComponenteDataFactory.criarComponenteComum())
            .when()
                .post("/v2/produtos/" + produtoId + "/componentes")
            .then()
                .body("message", equalTo("Componente de produto adicionado com sucesso"))
                .statusCode(201)
                .extract()
                .path("data.componenteId");
    }


    @Test
    @DisplayName("Validar a adição de um componente")
    public void testValidarAdicaoDeComponente() {
        ComponentePojo componenteValido = ComponenteDataFactory.criarComponenteComum();
       int ComponenteIdAdicionado = given().contentType(ContentType.JSON)
                .header("token", token)
                .body(componenteValido)
            .when()
                .post("/v2/produtos/" + produtoId + "/componentes")
            .then()
                .body("message", equalTo("Componente de produto adicionado com sucesso"))
                .statusCode(201)
               .extract()
               .path("data.componenteId");
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .delete("/v2/produtos/" + produtoId + "/componentes/" + ComponenteIdAdicionado)
            .then()
                .statusCode(204);

    }

    @Test
    @DisplayName("Validar a adição de um componente sem nome")
    public void testValidarAdicaoDeComponenteSemNome() {
        ComponentePojo componenteSemNome = ComponenteDataFactory.criarComponenteComum();
        componenteSemNome.setComponenteNome(null);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(componenteSemNome)
            .when()
                .post("/v2/produtos/" + produtoId + "/componentes")
            .then()
                .body("error", equalTo("componenteNome e componenteQuantidade são atributos obrigatórios"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Validar a adição de um componente sem quantidade")
    public void testValidarAdicaoDeComponenteSemQuantidade() {
        ComponentePojo componenteSemQuantidade = ComponenteDataFactory.criarComponenteComum();
        componenteSemQuantidade.setComponenteQuantidade(null);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(componenteSemQuantidade)
            .when()
                .post("/v2/produtos/" + produtoId + "/componentes")
            .then()
                .body("error", equalTo("componenteNome e componenteQuantidade são atributos obrigatórios"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Validar a adição de um componente sem token")
    public void testValidarAdicaoDeComponenteSemToken() {
        ComponentePojo componenteSemToken = ComponenteDataFactory.criarComponenteComum();
        given().contentType(ContentType.JSON)
                .body(componenteSemToken)
            .when()
                .post("/v2/produtos/" + produtoId + "/componentes")
            .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Validar a adição de um componente a um produto inexistente")
    public void testValidarAdicaoDeComponenteProdutoInexistente() {
        ComponentePojo componenteProdutoInexistente = ComponenteDataFactory.criarComponenteComum();
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(componenteProdutoInexistente)
            .when()
                .post("/v2/produtos/999999/componentes")
            .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Validar a adição de um componente com valor invalido")
    public void testValidarAdicaoDeComponenteValorInvalido() {
        ComponentePojo componenteValorInvalido = ComponenteDataFactory.criarComponenteComum();
        componenteValorInvalido.setComponenteQuantidade(0);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(componenteValorInvalido)
            .when()
                .post("/v2/produtos/" + produtoId + "/componentes")
            .then()
                .body("error", equalTo("A quantidade mínima para o componente não deve ser inferior a 1"))
                .statusCode(422);
    }

    @Test
    @DisplayName("Validar a busca de componentes")
    public void testValidarBuscaDeComponente() {
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .get("/v2/produtos/" + produtoId + "/componentes")
            .then()
                .statusCode(200)
                .body("message", equalTo("Listagem de componentes de produto realizada com sucesso"));
    }

    @Test
    @DisplayName("Validar a busca de componentes sem token")
    public void testValidarBuscaDeComponenteSemToken() {
        given().contentType(ContentType.JSON)
            .when()
                .get("/v2/produtos/" + produtoId + "/componentes")
            .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Validar a alteração de um componente")
    public void testValidarAlteracaoDeUmComponente() {
        ComponentePojo componenteAtualizado = ComponenteDataFactory.criarComponenteComum();
        componenteAtualizado.setComponenteNome("Componente atualizado");
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(componenteAtualizado)
            .when()
                .put("/v2/produtos/" + produtoId + "/componentes/" + componenteId)
            .then()
                .statusCode(200)
                .body("message", equalTo("Componente de produto alterado com sucesso"));
        given()
                .contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .get("/v2/produtos/" + produtoId + "/componentes/" + componenteId)
            .then()
                .statusCode(200)
                .body("data.componenteNome", equalTo("Componente atualizado"));
    }

    @Test
    @DisplayName("Validar a alteração de um componente sem Nome")
    public void testValidarAlteracaoDeUmComponenteSemNome() {
        ComponentePojo componenteAtualizadoSemNome = ComponenteDataFactory.criarComponenteComum();
        componenteAtualizadoSemNome.setComponenteNome(null);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(componenteAtualizadoSemNome)
            .when()
                .put("/v2/produtos/" + produtoId + "/componentes/" + componenteId)
            .then()
                .statusCode(400)
                .body("error", equalTo("componenteNome e componenteQuantidade são atributos obrigatórios"));
    }

    @Test
    @DisplayName("Validar a alteração de um componente sem Quantidade")
    public void testValidarAlteracaoDeUmComponenteSemQuantidade() {
        ComponentePojo componenteAtualizadoSemQuantidade = ComponenteDataFactory.criarComponenteComum();
        componenteAtualizadoSemQuantidade.setComponenteQuantidade(null);
        given().contentType(ContentType.JSON)
                .header("token", token)
                .body(componenteAtualizadoSemQuantidade)
            .when()
                .put("/v2/produtos/" + produtoId + "/componentes/" + componenteId)
            .then()
                .statusCode(400)
                .body("error", equalTo("componenteNome e componenteQuantidade são atributos obrigatórios"));
    }

    @Test
    @DisplayName("Validar a alteração de um componente sem Token")
    public void testValidarAlteracaoDeUmComponenteSemToken() {
        ComponentePojo componenteAtualizado = ComponenteDataFactory.criarComponenteComum();
        componenteAtualizado.setComponenteNome("Componente atualizado");
        given().contentType(ContentType.JSON)
                .body(componenteAtualizado)
            .when()
                .put("/v2/produtos/" + produtoId + "/componentes/" + componenteId)
            .then()
                .statusCode(401);
    }

    @Test
    @DisplayName("Validar a alteração de um componente inexistente")
    public void testValidarAlteracaoDeUmComponenteInexitente() {
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .put("/v2/produtos/" + produtoId + "/componentes/99999999")
            .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Validar a remoção de um componente ")
    public void testValidarRemocaoDeUmComponente() {
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .delete("/v2/produtos/" + produtoId + "/componentes/" + componenteId)
            .then()
                .statusCode(204);
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .get("/v2/produtos/" + produtoId + "/componentes/" + componenteId)
            .then()
                .statusCode(404)
        ;
    }

    @Test
    @DisplayName("Validar a remoção de um componente sem token")
    public void testValidarRemocaoDeUmComponenteSemToken() {
        given().contentType(ContentType.JSON)
            .when()
                .delete("/v2/produtos/" + produtoId + "/componentes/" + componenteId)
            .then()
                .statusCode(401);
    }
    @Test
    @DisplayName("Validar a remoção de um componente inexistente")
    public void testValidarRemocaoDeUmComponenteInexistente() {
        given().contentType(ContentType.JSON)
                .header("token", token)
            .when()
                .delete("/v2/produtos/" + produtoId + "/componentes/999999999999")
            .then()
                .statusCode(404);
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
