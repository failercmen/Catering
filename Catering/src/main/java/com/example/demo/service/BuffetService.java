package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Piatto;
import com.example.demo.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	private BuffetRepository buffetRepository;

	@Autowired
	private PiattoService piattoService;


	@Transactional
	public Buffet inserisci(Buffet buffet) {
		return buffetRepository.save(buffet);
	}

	@Transactional
	public List<Buffet> tutti() {
		return (List<Buffet>) buffetRepository.findAll();
	}

	@Transactional
	public Buffet buffetPerId(Long id) {
		Optional<Buffet> optional = buffetRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else
			return null;
	}
	
//	@Transactional
//	public Buffet buffetPerNome(String nome) {
//		Optional<Buffet> optional = buffetRepository.findByNome(nome);
//		if (optional.isPresent())
//			return optional.get();
//		else
//			return null;
//	}
	

	@Transactional
	public boolean alreadyExists(Buffet buffet) {
		List<Buffet> prodotti = this.buffetRepository.findByNome(buffet.getNome());
		if (prodotti.size() > 0)
			return true;
		else
			return false;
	}

	// lo usiamo per prendere i piatti per un buffet, usato poi per la stampa
	// dei piatti del buffet

	public List<Piatto> piattiBuffet(Long id) {

		List<Piatto> piatti = this.buffetPerId(id).getListaPiatti();

		return piatti;
	}

	@Transactional
	public void deleteById(Long id) {
		buffetRepository.deleteById(id);
	}

	@Transactional
	public void eliminaPiattoDaBuffet(Buffet buffet, Piatto piatto) {
		buffet.removePiatto(piatto);
		buffetRepository.save(buffet);

	}


	

	// TODO vedi se funziona
	@Transactional
	public void updateBuffet(Long idBuffetUpdate, Buffet buffetNuovo) {
		// prova a inserire un buffet e tutte le sue variabili,
		// prendendo di riferimento queste setti tutte le variabili come quello nuovo
		Buffet buffet = this.buffetPerId(idBuffetUpdate);
		buffet.setChef(buffetNuovo.getChef());
		buffet.setDescrizione(buffetNuovo.getDescrizione());
		buffet.setNome(buffetNuovo.getNome());
		buffet.setListaPiatti(buffetNuovo.getListaPiatti());

		// nuovo, salva effettivamente ma funziona strano
		for (Piatto p : buffet.getListaPiatti()) {
			p.getListaBuffet().add(buffet);
		}

		// questo delete da error
		// buffetRepository.deleteById(idBuffetUpdate);
		buffetRepository.save(buffet);
	}

}
