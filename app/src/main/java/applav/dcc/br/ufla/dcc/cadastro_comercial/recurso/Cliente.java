package applav.dcc.br.ufla.dcc.cadastro_comercial.recurso;

public class Cliente extends Pessoa {
	private int ptsFidelidade;
	public Cliente(String nome, String sobrenome, String cPF, String telefone, String email,
			String dataNascimento) {
		super(nome, sobrenome, cPF, telefone, email, dataNascimento);
		ptsFidelidade = 0;
	}
	public Cliente (String nome, String sobrenome,String cpf){
		super(nome,sobrenome,cpf);
	}
	
	public int getPtsFidelidade() {
		return ptsFidelidade;
	}
	
	public void setPtsFidelidade(int ptsFidelidade) {
		this.ptsFidelidade = ptsFidelidade;
	}

}
