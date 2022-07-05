package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.repository.PiattoRepository;

@Service
public class PiattoService {

	@Autowired
	private PiattoRepository piattoRepository; 
	
	
	@Transactional
	public Piatto inserisci(Piatto prodotto) {
		return piattoRepository.save(prodotto);
	}

	@Transactional
	public List<Piatto> tutti() {
		return (List<Piatto>) piattoRepository.findAll();
	}

	@Transactional
	public Piatto piattoPerId(Long id) {
		Optional<Piatto> optional = piattoRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	
	
	//lo usiamo per prendere gli ingredienti di un piatto, usato poi per la stampa 
	//degli inredienti del piatto
	@Transactional
	public List<Ingrediente> ingredientiPiatto(Long id) {
		List<Ingrediente> ingr = this.piattoPerId(id).getListaIngredienti();
		return ingr;
	}

	 
	@Transactional
	public boolean alreadyExists(Piatto piatto) {
		List<Piatto> piatti = this.piattoRepository.findByNome(piatto.getNome());
		if (piatti.size() > 0)
			return true;
		else 
			return false;
	}
	
	
	
	@Transactional
	public void deleteById(Long id) {
	 	piattoRepository.deleteById(id);
	}
	
	
	@Transactional
	public void eliminaIngredienteDaPiatto(Piatto piatto, Ingrediente ingrediente) {
		piatto.removeIngrediente(ingrediente);
		piattoRepository.save(piatto);
	
	}
	
	
	//usato per trovare un Buffet specifico nella lista dei buffet associati al piatto
	//....restituisce il buffet stesso
	@Transactional
	public Buffet buffetCorrispondente(Piatto piatto,Long idBuffet) {
		
		for(Buffet b: piatto.getListaBuffet()) {
			if(b.getId()==idBuffet)
				return b;
		}
		return null;
	}
	
	
	@Transactional
	public List<Piatto> piattiNonPresenti(Buffet buffet) {

			List<Piatto> piatti = this.tutti();
			for (Piatto p : buffet.getListaPiatti()) {
				piatti.remove(p);
			}
			return piatti;
		}
	
		
}
