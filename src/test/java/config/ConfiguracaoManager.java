package config;

import org.aeonbits.owner.ConfigFactory;

public final class ConfiguracaoManager {

    private static ConfiguracaoPropriedades configuracao;

    private ConfiguracaoManager() {}

    public static synchronized ConfiguracaoPropriedades getConfiguracao() {
        if (configuracao == null) {
            configuracao = ConfigFactory.create(ConfiguracaoPropriedades.class);
        }
        return configuracao;
    }
}
