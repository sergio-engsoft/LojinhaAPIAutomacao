package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
    "system:properties",
    "system:env",
    "classpath:config.properties"
})
public interface ConfiguracaoPropriedades extends Config {

    @Key("api.base.uri")
    String baseUri();

    @Key("api.base.path")
    String basePath();

    @Key("api.admin.login")
    String adminLogin();

    @Key("api.admin.password")
    String adminPassword();

    @Key("api.admin.nome")
    String adminNome();
}
