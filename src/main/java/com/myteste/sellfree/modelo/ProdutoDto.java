package com.myteste.sellfree.modelo;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;

public class ProdutoDto {
	@NotEmpty(message="Nome não pode ser nulo!")
	private String nome;
	
	@NotEmpty(message="Nome não pode ser nulo!")
	private String brand;
	
	@NotEmpty(message="Nome não pode ser nulo!")
	private String categoria;
	
	@Min(0)
	private double preco;
	
	@Size(min=10, message= "A descrição precisa ser maior do que 10 caracteres")
	@Size(max=1000, message="A descrição precisa ser menor do que 1000 caracteres")
	private String descricao;
	
	private MultipartFile urlImagem;

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

	public MultipartFile getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(MultipartFile urlImagem) {
		this.urlImagem = urlImagem;
	}
	
	
	
}
