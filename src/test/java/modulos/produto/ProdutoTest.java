package modulos.produto;
import datafactory.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes de API Rest do modulo de Produto")
public class ProdutoTest {

    private String token;

    @BeforeEach
    public void beforeEach(){
        baseURI = "http://165.227.93.41";
        basePath = "/lojinha";

        // Obter o token do usuario admin
        this.token =  given()
                .contentType(ContentType.JSON)
                .body(UsuarioDataFactory.criarusuarioadministrador())
                .when()
                    .post("/v2/login")
                .then()
                .extract()
                    .path("data.token");
    }

    @Test
    @DisplayName("Validar os limites proibidos do valor do Produto")
    public void testValidarLimitesZeradoProibidoValorProduto() {

        // Validar o erro ao tentar inserir um valor invalido "<0.01" para o produto e o status code 422

        given().contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.00))
                .when()
                    .post("/v2/produtos")
                .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
    }
        @Test
        @DisplayName("Validar os limites proibidos do valor do Produto")
        public void testValidarLimitesMaiorSeteMilProibidoValorProduto(){

        // Validar o erro ao temtar inserir um valor invalido ">7000" para o produto e o status code 422

            given().contentType(ContentType.JSON)
                    .header("token", this.token)
                    .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(7001.00))
                .when()
                    .post("/v2/produtos")
                .then()
                    .assertThat()
                        .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                        .statusCode(422);
        }

        @Test
        @DisplayName("Validar os limites Adequados do valor do Produto")
        public void testValidarLimitesAdequadosValorProduto(){

            //validar a insersão de produto com valor valido e o status code 201

            given().contentType(ContentType.JSON)
                    .header("token", this.token)
                    .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(200.00))
                    .when()
                    .post("/v2/produtos")
                    .then()
                    .assertThat()
                        .statusCode(201)
                    .extract()
                        .path("data");
        }

    @Test
    @DisplayName("Validar falha ao tentar criar um produto com usuario não autorizado")
    public void testValidarFalhaDeUsuarioNaoAutorizadoProduto(){

        given().contentType(ContentType.JSON)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(200))
                .when()
                    .post("/v2/produtos")
                .then()
                    .assertThat()
                        .statusCode(401);

    }
    }