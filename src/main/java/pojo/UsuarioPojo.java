package pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioPojo {

    private String usuarioLogin;
    private String usuarioSenha;
    private String usuarioNome;

    public String getUsuarioLogin() { return usuarioLogin; }
    public void setUsuarioLogin(String usuarioLogin) { this.usuarioLogin = usuarioLogin; }

    public String getUsuarioSenha() { return usuarioSenha; }
    public void setUsuarioSenha(String usuarioSenha) { this.usuarioSenha = usuarioSenha; }

    public String getUsuarioNome() { return usuarioNome; }
    public void setUsuarioNome(String usuarioNome) { this.usuarioNome = usuarioNome; }
}
