package datafactory;

import pojo.ComponentePojo;
import pojo.ProdutoPojo;

import java.util.List;

public final class ProdutoDataFactory {

    public static final double VALOR_ABAIXO_DO_MINIMO = 0.00;
    public static final double VALOR_ACIMA_DO_MAXIMO  = 7001.00;

    private ProdutoDataFactory() {}

    public static ProdutoPojo criarProdutoValido() {
        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoNome("Produto Teste Automacao");
        produto.setProdutoValor(100.99);
        produto.setProdutoCores(List.of("Preto", "Branco"));
        produto.setProdutoUrlMock("");
        produto.setComponente(List.of(
            componente("Controle", 2),
            componente("Cabo De Energia", 1)
        ));
        return produto;
    }

    public static ProdutoPojo criarProdutoComValorAbaixoDoMinimo() {
        ProdutoPojo produto = criarProdutoValido();
        produto.setProdutoValor(VALOR_ABAIXO_DO_MINIMO);
        return produto;
    }

    public static ProdutoPojo criarProdutoComValorAcimaDoMaximo() {
        ProdutoPojo produto = criarProdutoValido();
        produto.setProdutoValor(VALOR_ACIMA_DO_MAXIMO);
        return produto;
    }

    public static ProdutoPojo criarProdutoSemNome() {
        ProdutoPojo produto = criarProdutoValido();
        produto.setProdutoNome(null);
        return produto;
    }

    public static ProdutoPojo criarProdutoSemValor() {
        ProdutoPojo produto = criarProdutoValido();
        produto.setProdutoValor(null);
        return produto;
    }

    public static ProdutoPojo criarProdutoSemCores() {
        ProdutoPojo produto = criarProdutoValido();
        produto.setProdutoCores(null);
        return produto;
    }

    private static ComponentePojo componente(String nome, int quantidade) {
        ComponentePojo c = new ComponentePojo();
        c.setComponenteNome(nome);
        c.setComponenteQuantidade(quantidade);
        return c;
    }
}
