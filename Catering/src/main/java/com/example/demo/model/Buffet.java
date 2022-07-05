package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Buffet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank // no spazi vuoti o bianchi
	private String nome;

	@NotBlank
	private String descrizione;

	
	@ManyToOne(fetch = FetchType.EAGER)
	private Chef chef;
	

	@ManyToMany(mappedBy = "listaBuffet")
	private List<Piatto> listaPiatti;

	
	public List<Piatto> getListaPiatti() {
		return listaPiatti;
	}


	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void setListaPiatti(List<Piatto> listaPiatti) {
		this.listaPiatti = listaPiatti;
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
	
	public void removePiatto(Piatto piatto) {
		for(Piatto p : this.getListaPiatti()) {
			if(p.getNome().equals(piatto.getNome())) {
				this.listaPiatti.remove(p);
			}
		}
	}
	

}
