package datafactory;

import config.ConfiguracaoManager;
import pojo.UsuarioPojo;

public final class UsuarioDataFactory {

    private UsuarioDataFactory() {}

    public static UsuarioPojo criarUsuarioAdministrador() {
        var config = ConfiguracaoManager.getConfiguracao();
        return criarUsuario(config.adminLogin(), config.adminPassword(), config.adminNome());
    }

    public static UsuarioPojo criarPayloadLogin(String login, String senha) {
        return criarUsuario(login, senha, null);
    }

    public static UsuarioPojo criarUsuario(String login, String senha, String nome) {
        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioLogin(login);
        usuario.setUsuarioSenha(senha);
        usuario.setUsuarioNome(nome);
        return usuario;
    }

    public static UsuarioPojo criarUsuarioSemNome() {
        return criarUsuario("usuario_teste", "senha_teste", null);
    }

    public static UsuarioPojo criarUsuarioSemSenha() {
        return criarUsuario("usuario_teste", null, "Nome Teste");
    }

    public static UsuarioPojo criarUsuarioSemLogin() {
        return criarUsuario(null, "senha_teste", "Nome Teste");
    }
}
