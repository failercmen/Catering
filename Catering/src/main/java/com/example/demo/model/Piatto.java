package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Piatto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank // no spazi vuoti o bianchi
	private String nome;

	@NotBlank // no spazi vuoti o bianchi
	private String descrizione;

	
	@ManyToMany //(mappedBy = "listaPiatti")
	private List<Buffet> listaBuffet;

	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Ingrediente> listaIngredienti;

	
	public List<Buffet> getListaBuffet() {
		return listaBuffet;
	}

	public void setListaBuffet(List<Buffet> listaBuffet) {
		this.listaBuffet = listaBuffet;
	}

	public List<Ingrediente> getListaIngredienti() {
		return listaIngredienti;
	}

	public void setListaIngredienti(ArrayList<Ingrediente> listaIngredienti) {
		this.listaIngredienti = listaIngredienti;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setListaIngredienti(List<Ingrediente> listaIngredienti) {
		this.listaIngredienti = listaIngredienti;
	}
	
	public void removeIngrediente(Ingrediente ingrediente) {
		for(Ingrediente i : this.getListaIngredienti()) {
			if(i.getNome().equals(ingrediente.getNome())) {
				this.listaIngredienti.remove(i);
			}
		}
	}
	

}
