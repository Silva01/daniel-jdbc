package br.com.daniel.jdbc.teste.bean;

import br.com.daniel.jdbc.mapeamento.Coluna;

public class TesteBean {

	private Integer id;
	private String nome;
	private Integer idade;
	
	public Integer getId() {
		return id;
	}
	
	@Coluna("ID")
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	@Coluna("NOME")
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getIdade() {
		return idade;
	}
	
	@Coluna("IDADE")
	public void setIdade(Integer idade) {
		this.idade = idade;
	}	
}
