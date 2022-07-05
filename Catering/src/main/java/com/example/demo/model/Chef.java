package com.example.demo.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Chef {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank //no spazi vuoti o bianchi
	private String nome;
	
	@NotBlank 
	private String cognome;
	
	@NotBlank
	private String nazionalita;
	
	@OneToMany(mappedBy = "chef")
	private List<Buffet> listaBuffet;
	
	
	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public List<Buffet> getListaBuffet() {
		return listaBuffet;
	}

	public void setListaBuffet(List<Buffet> listaBuffet) {
		this.listaBuffet = listaBuffet;
	}
	
	
	
	
	
}
