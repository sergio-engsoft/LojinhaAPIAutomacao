package datafactory;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDataFactory {

    public static ProdutoPojo criarProdutoComum() {
        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoNome("Produto teste");
        produto.setProdutoValor(100.99);

        List<String> cores = new ArrayList<>();
        cores.add("Preto");
        cores.add("Branco");
        produto.setProdutoCores(cores);

        produto.setProdutoUrlMock("");

        List<ComponentePojo> componentes = new ArrayList<>();

        ComponentePojo componente = new ComponentePojo();
        componente.setComponenteNome("Controle");
        componente.setComponenteQuantidade(2);

        ComponentePojo segundoComponente = new ComponentePojo();
        segundoComponente.setComponenteNome("Cabo De Energia");
        segundoComponente.setComponenteQuantidade(1);

        componentes.add(componente);
        componentes.add(segundoComponente);

        produto.setComponente(componentes);
        return produto;
    }
}