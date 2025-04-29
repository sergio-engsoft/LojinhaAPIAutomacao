package datafactory;

import pojo.UsuarioPojo;

public class UsuarioDataFactory {

    public static UsuarioPojo criarUsuarioAdministrador(){
        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioLogin("admin");
        usuario.setUsuarioSenha("admin");
        usuario.setUsuarioNome("admin");
        return usuario;
    }

    public static UsuarioPojo criarusuario(String login, String senha, String nome){
        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioLogin(login);
        usuario.setUsuarioSenha(senha);
        usuario.setUsuarioNome(nome);
        return usuario;
    }
}
