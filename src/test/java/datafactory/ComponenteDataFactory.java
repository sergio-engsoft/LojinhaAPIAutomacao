package datafactory;

import pojo.ComponentePojo;

public final class ComponenteDataFactory {

    public static final int QUANTIDADE_MINIMA_VALIDA    = 1;
    public static final int QUANTIDADE_ABAIXO_DO_MINIMO = 0;

    private ComponenteDataFactory() {}

    public static ComponentePojo criarComponenteValido() {
        ComponentePojo componente = new ComponentePojo();
        componente.setComponenteNome("Componente Teste Automacao");
        componente.setComponenteQuantidade(QUANTIDADE_MINIMA_VALIDA);
        return componente;
    }

    public static ComponentePojo criarComponenteSemNome() {
        ComponentePojo componente = criarComponenteValido();
        componente.setComponenteNome(null);
        return componente;
    }

    public static ComponentePojo criarComponenteSemQuantidade() {
        ComponentePojo componente = criarComponenteValido();
        componente.setComponenteQuantidade(null);
        return componente;
    }

    public static ComponentePojo criarComponenteComQuantidadeAbaixoDoMinimo() {
        ComponentePojo componente = criarComponenteValido();
        componente.setComponenteQuantidade(QUANTIDADE_ABAIXO_DO_MINIMO);
        return componente;
    }

    public static ComponentePojo criarComponenteComNome(String nome) {
        ComponentePojo componente = criarComponenteValido();
        componente.setComponenteNome(nome);
        return componente;
    }
}
