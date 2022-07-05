package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.repository.BuffetRepository;
import com.example.demo.repository.IngredienteRepository;
import com.example.demo.repository.PiattoRepository;

@Service
public class IngredienteService {

	@Autowired
	private IngredienteRepository ingredienteRepository;

	@Autowired
	private BuffetRepository buffetRepository;

	@Autowired
	private PiattoRepository piattoRepository;

	@Transactional
	public Ingrediente inserisci(Ingrediente ingrediente) {
		return ingredienteRepository.save(ingrediente);
	}

	@Transactional
	public List<Ingrediente> tutti() {
		return (List<Ingrediente>) ingredienteRepository.findAll();
	}

	@Transactional
	public Ingrediente ingredientePerId(Long id) {
		Optional<Ingrediente> optional = ingredienteRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else
			return null;
	}

	@Transactional
	public boolean alreadyExists(Ingrediente ingrediente) {
		List<Ingrediente> prodotti = this.ingredienteRepository.findByNome(ingrediente.getNome());
		if (prodotti.size() > 0)
			return true;
		else
			return false;
	}

	@Transactional
	public void deleteById(Long id) {
		ingredienteRepository.deleteById(id);
	}

	// mi faccio restituire tutti i piatti del buffet , controllo se presente
	// l'ingrediente,
	// se presente lo elimino

//	@Transactional
//	public List<Piatto> piattiDiIngredienti(Long id) {
//
//		List<Piatto> listaPiattiXIngrediente = new ArrayList<Piatto>();
//
//		for (Buffet b : buffetRepository.findAll()) {
//			for (Piatto p : b.getListaPiatti()) {
//				for (Ingrediente i : p.getListaIngredienti()) {
//					if (i.getId() == id)
//						listaPiattiXIngrediente.add(p);
//				}
//			}
//		}
//		return listaPiattiXIngrediente;
//	}
	
//	@Transactional
//	public List<Piatto> piattiDiIngredienti(Ingrediente ingr) {
//
//		List<Piatto> listaPiattiXIngrediente = new ArrayList<Piatto>();
//
//		for (Buffet b : buffetRepository.findAll()) {
//			for (Piatto p : b.getListaPiatti()) {
//				for (Ingrediente i : p.getListaIngredienti()) {
//					
//					if (i.getNome().equals(ingr.getNome()))
//						listaPiattiXIngrediente.add(p);
//				}
//			}
//		}
//		return listaPiattiXIngrediente;
//	}
	
	
	@Transactional
	public List<Piatto> piattiDiIngredienti(Ingrediente ingr) {

		List<Piatto> listaPiattiXIngrediente = new ArrayList<Piatto>();

			for (Piatto p : piattoRepository.findAll()) {
				for (Ingrediente i : p.getListaIngredienti()) {
					if (i.getId() == ingr.getId())
						listaPiattiXIngrediente.add(p);
				}
			}
		
		return listaPiattiXIngrediente;
	}

	// mi faccio restituire tutti i piatti del buffet , controllo se presente
	// l'ingrediente,
	// se presente lo elimino

}