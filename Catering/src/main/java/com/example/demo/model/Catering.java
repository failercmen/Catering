package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;



/* QUI è DOVE AVREMO IL NOSTRO PROGETTO JPA CON 4/5 CLASSI*/

@Entity
public class Catering {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank //no spazi vuoti o bianchi
	private String nome;	
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Buffet> listaBuffet;
	
	
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

	public List<Buffet> getListaBuffet() {
		return listaBuffet;
	}

	public void setListaBuffet(ArrayList<Buffet> listaBuffet) {
		this.listaBuffet = listaBuffet;
	}
	
	
	
	
	
	
}
