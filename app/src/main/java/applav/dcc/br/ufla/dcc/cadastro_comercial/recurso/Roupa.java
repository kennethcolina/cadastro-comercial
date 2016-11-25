package applav.dcc.br.ufla.dcc.cadastro_comercial.recurso;

public class Roupa implements Comparable<Roupa> {
	private String referencia;
	private String nome;
	private String codigo;
	private double preco;
	private String cor;
	private String tamanho;
	private String material;
	private String detalhes;

	public Roupa(){}

	public Roupa(String referencia,String nome,String codigo, double preco, String cor, String tamanho, String material,
			String detalhes) {
		this.referencia = referencia;
		this.nome = nome;
		this.codigo = codigo;
		this.preco = preco;
		this.cor = cor;
		this.tamanho = tamanho;
		this.material = material;
		this.detalhes = detalhes;
	}

	public Roupa(String nome,String codigo, double preco) {
		this.nome = nome;
		this.codigo = codigo;
		this.preco = preco;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getNome() {
		return nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public String getCor() {
		return cor;
	}

	public String getTamanho() {
		return tamanho;
	}

	public String getMaterial() {
		return material;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}


	@Override
	public int compareTo(Roupa roupa) {
		return this.getNome().compareToIgnoreCase(roupa.getNome());
	}
	
}
