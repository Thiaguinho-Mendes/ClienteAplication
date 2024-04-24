package br.com.wmw.domain;

public class Cliente {

	private Integer codigo;
	private String nome;
	private TipoPessoa tipoPessoa;
	private String cpfCnpj;
	private String telefone;
	private String email;
	public Origem origem;

	public Cliente() {}
	
	public Cliente(String nome, TipoPessoa tipoPessoa, String cpfCnpj, String telefone, String email, Origem origem) {
		this.nome = nome;
		this.tipoPessoa = tipoPessoa;
		this.cpfCnpj = cpfCnpj;
		this.telefone = telefone;
		this.email = email;
		this.origem = origem;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public TipoPessoa getTipoPessoaOriginal() {
		return tipoPessoa;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipoPessoaOriginal(TipoPessoa tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}
	
	public void setTipoPessoa(String tipoPessoa) {
		setTipoPessoaOriginal(TipoPessoa.valueOf(tipoPessoa));
	}
	
	public String getTipoPessoa() {
		return getTipoPessoaOriginal().name();
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Origem getOrigemOriginal() {
		return origem;
	}
	
	public String getOrigem() {
		return getOrigemOriginal().name();
	}

	public void setOrigemOriginal(Origem origem) {
		this.origem = origem;
	}
	
	public void setOrigem(String origem) {
		setOrigemOriginal(Origem.valueOf(origem));
	}


}
