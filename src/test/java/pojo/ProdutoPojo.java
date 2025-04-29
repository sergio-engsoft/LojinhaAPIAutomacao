package pojo;
import java.util.List;

public class ProdutoPojo {
    private String produtoNome;
    private Double produtoValor;
    private List<String> produtoCores;
    private String produtoUrlMock;
    private List<ComponentePojo> componente;

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public Double getProdutoValor() {
        return produtoValor;
    }

    public void setProdutoValor(Double produtoValor) {
        this.produtoValor = produtoValor;
    }

    public List<String> getProdutoCores() {
        return produtoCores;
    }

    public void setProdutoCores(List<String> produtoCores) {
        this.produtoCores = produtoCores;
    }

    public String getProdutoUrlMock() {
        return produtoUrlMock;
    }

    public void setProdutoUrlMock(String produtoUrlMock) {
        this.produtoUrlMock = produtoUrlMock;
    }

    public List<ComponentePojo> getComponente() {
        return componente;
    }

    public void setComponente(List<ComponentePojo> componente) {
        this.componente = componente;
    }
}