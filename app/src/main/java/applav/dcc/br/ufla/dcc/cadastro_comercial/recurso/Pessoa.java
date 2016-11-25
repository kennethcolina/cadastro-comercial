package applav.dcc.br.ufla.dcc.cadastro_comercial.recurso;


public abstract class Pessoa implements Comparable<Pessoa>{
	private String nome;
	private String sobrenome;
	private String CPF;
	private String telefone;
	private String email;
	private String dataNascimento;

	public Pessoa(String nome, String sobrenome, String cPF, String telefone, String email,
			String dataNascimento) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		CPF = cPF;
		this.telefone = telefone;
		this.email = email;
		this.dataNascimento = dataNascimento;}

	public Pessoa(String nome,String sobrenome, String cpf){
		this.nome = nome;
		this.sobrenome = sobrenome;
		CPF = cpf;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public String getCPF() {
		return CPF;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getEmail() {
		return email;
	}



	public String getDataNascimento() {	return dataNascimento;}

	@Override
	public int compareTo(Pessoa pessoa) {
		return this.getNome().compareToIgnoreCase(pessoa.getNome());
	}


}
