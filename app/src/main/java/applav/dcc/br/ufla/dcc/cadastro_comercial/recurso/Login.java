package applav.dcc.br.ufla.dcc.cadastro_comercial.recurso;


public class Login {

	private String usuario;
	private String senha;
	
	public Login(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getSenha() {
		return senha;
	}
}
