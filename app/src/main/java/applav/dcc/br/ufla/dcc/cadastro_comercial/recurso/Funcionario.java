package applav.dcc.br.ufla.dcc.cadastro_comercial.recurso;


public class Funcionario extends Pessoa {
	private float salario;
	private float comissao;
	private String dataInicio;
	private Login login;
	private int nivel;
	
	public Funcionario(String nome, String sobrenome, String cPF, String telefone,float salario,float comissao, String email,
			String dataNascimento,String dataInicio, Login login, int nivel) {
		super(nome, sobrenome, cPF, telefone, email, dataNascimento);
		this.salario = salario;
		this.comissao = comissao;
		this.login = login;
		this.dataInicio = dataInicio;
		this.nivel = nivel;
	}

	public Funcionario (String nome, String sobrenome,String cpf){
		super(nome,sobrenome,cpf);
	}

	public float getSalario() {
		return salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}

	public float getComissao() {
		return comissao;
	}

	public void setComissao(float comissao) {
		this.comissao = comissao;
	}

	public String getDataInicio() { return dataInicio;}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	
	public Login getLogin() {
		return login;
	}

}
