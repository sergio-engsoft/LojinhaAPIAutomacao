package datafactory;
import pojo.ComponentePojo;

public class ComponenteDataFactory {
    public static ComponentePojo criarComponenteComum() {
        ComponentePojo componente = new ComponentePojo();
        componente.setComponenteNome("Componente teste");
        componente.setComponenteQuantidade(2);
    return componente;
    }
}