package com.myteste.sellfree.modelo;

import java.util.Date;

import jakarta.persistence.*;

@Entity //dizer q vai ser uma tabela no banco
@Table(name = "produtos") //permite criar uma tabela no banco com o nome escolhido
public class Produto {

	@Id //notacao de id pra dizer q o atributo abaixo é id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //sempre q gerar um id vai seguir o padrao
	private int id;
	
	private String nome;
	private String brand;
	private String categoria;
	private double preco;
	
	@Column(columnDefinition = "TEXT") //define q essa vai ser coluna do tipo texto
	private String descricao; //senao seria do tipo var e etc..
	private Date criadoEm; 
	private String urlImagem;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getCriadoEm() {
		return criadoEm;
	}
	public void setCriadoEm(Date criadoEm) {
		this.criadoEm = criadoEm;
	}
	public String getUrlImagem() {
		return urlImagem;
	}
	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}
	
	
}
