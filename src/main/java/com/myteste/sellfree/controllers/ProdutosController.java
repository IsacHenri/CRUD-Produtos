package com.myteste.sellfree.controllers;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myteste.sellfree.modelo.Produto;
import com.myteste.sellfree.modelo.ProdutoDto;
import com.myteste.sellfree.servicos.ProdutosRepository;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	@Autowired
	private ProdutosRepository repo;
	
	@GetMapping({"","/"})
	public String mostraListaProduto(Model model) {
		List<Produto> produtos = repo.findAll(Sort.by(Sort.Direction.DESC,"id"));
		model.addAttribute("produtos", produtos);
	    return "produtos/index"; // Replace with the actual name of your view/template
	}
	
	@GetMapping({"/criar"})
	public String mostrarFormProduto(Model model) {
		ProdutoDto produtoDto = new ProdutoDto();
		model.addAttribute("produtoDto", produtoDto);
		return "produtos/CriarProduto";
	}
	
	@PostMapping({"/criar"})
	public String criarProduto(
			@Valid @ModelAttribute ProdutoDto produtoDto,
			BindingResult result
			) {
		
		if(produtoDto.getUrlImagem().isEmpty()) {
			result.addError(new FieldError("produtoDto", "urlImagem", "É preciso ter algum arquivo de imagem."));
		}
		
		if(result.hasErrors()) {
			return "produtos/CriarProduto";
		}
		
		// salvando o arquivo da imagem
		MultipartFile image = produtoDto.getUrlImagem();
		Date criadoEm = new Date();
		String storageFileName = criadoEm.getTime() + "_" + image.getOriginalFilename();
		
		try {
			String salvarDir = "public/images/";
			Path salvarPath = Paths.get(salvarDir);
			
			if(!Files.exists(salvarPath)) {
				Files.createDirectories(salvarPath);
			}
			
			try(InputStream inputStream = image.getInputStream()){
				Files.copy(inputStream, Paths.get(salvarDir + storageFileName),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch(Exception ex) {
			System.out.println("Exceção:" + ex.getMessage());
		}
		
		Produto produto = new Produto();
		produto.setNome(produtoDto.getNome());
		produto.setBrand(produtoDto.getBrand());
		produto.setCategoria(produtoDto.getCategoria());
		produto.setPreco(produtoDto.getPreco());
		produto.setDescricao(produtoDto.getDescricao());
		produto.setCriadoEm(criadoEm);
		produto.setUrlImagem(storageFileName);
		
		repo.save(produto);
		
		return "redirect:/produtos";
	}
	
	@GetMapping("/editar")
	public String showEditPage(
	Model model,
	@RequestParam int id) {

		try {
			Produto produto = repo.findById(id).get();

			model.addAttribute("produto", produto);

			ProdutoDto produtoDto = new ProdutoDto();
			produtoDto.setNome (produto.getNome());
			produtoDto.setBrand (produto.getBrand());
			produtoDto.setCategoria (produto.getCategoria());
			produtoDto.setPreco (produto.getPreco());

			produtoDto.setDescricao(produto.getDescricao());

			model.addAttribute("produtoDto", produtoDto);
		
			}
		catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/produtos";
		}
		
		return "produtos/EditarProduto";
	}
	
	@PostMapping({"/editar"})
	public String atualizarProduto(
			Model model,
			@RequestParam int id,
			@Valid @ModelAttribute ProdutoDto produtoDto,
			BindingResult result
			) {
		
		try {
			Produto produto = repo.findById(id).get();
			model.addAttribute("produto", produto);
			
			if(result.hasErrors()) {
				return "/produtos/EditarProduto";
			}
			
			if(!produtoDto.getUrlImagem().isEmpty()) {
				String atualizarDir = "public/images/";
				Path velhaImagemPath = Paths.get(atualizarDir + produto.getUrlImagem());
				
				try {
					Files.delete(velhaImagemPath);	
				} 
				catch(Exception ex) {
					System.out.println("Exceção:" + ex.getMessage());
				}
				
				
				// salvando o novo arquivo da imagem
				MultipartFile image = produtoDto.getUrlImagem();
				Date criadoEm = new Date();
				String storageFileName = criadoEm.getTime() + "_" + image.getOriginalFilename();
				
				try {
					String salvarDir = "public/images/";
					Path salvarPath = Paths.get(salvarDir);
					
					if(!Files.exists(salvarPath)) {
						Files.createDirectories(salvarPath);
					}
					
					try(InputStream inputStream = image.getInputStream()){
						Files.copy(inputStream, Paths.get(salvarDir + storageFileName),
								StandardCopyOption.REPLACE_EXISTING);
					}
					
					produto.setUrlImagem(storageFileName);
					
				} catch(Exception ex) {
					System.out.println("Exceção:" + ex.getMessage());
				}
			}
			
			produto.setNome(produtoDto.getNome());
			produto.setBrand(produtoDto.getBrand());
			produto.setCategoria(produtoDto.getCategoria());
			produto.setPreco(produtoDto.getPreco());
			produto.setDescricao(produtoDto.getDescricao());
			
			repo.save(produto);
			
		}catch(Exception ex) {
			System.out.println("Exceção:" + ex.getMessage());
		}
		return "redirect:/produtos";
	}
	
	@GetMapping({"/excluir"})
	public String excluirProduto(@RequestParam int id) {
		

		try {
			Produto produto = repo.findById(id).get();
			
			Path imagePath = Paths.get("public/images/" + produto.getUrlImagem());
			
			try {
				Files.delete(imagePath);	
			} 
			catch(Exception ex) {
				System.out.println("Exceção:" + ex.getMessage());
			}
			
			//deletando o produto
			repo.delete(produto);
		} 
		catch(Exception ex) {
			System.out.println("Exceção:" + ex.getMessage());
		}
		return "redirect:/produtos";
	}
	
}
