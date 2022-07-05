package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Utente;
import com.example.demo.repository.UtenteRepository;

@Service
public class UtenteService {

	
	@Autowired // carica un instanza in automatico di personaRepository
	private UtenteRepository ur;
	
	
	@Transactional
	public void save(Utente utente) {
		ur.save(utente);
	}
	
//	/* interrogazione non transazionale*/
//	public Utente findById(Long id) {
//		return ur.findById(id).get();
//	}
//	
//	/* interrogazione non transazionale*/
//	public List<Utente> findAll(){
//		List<Utente> persone = new ArrayList<Utente>();
//		
//		for(Utente p: ur.findAll()) {   //metodo già offeto da CRUD-Repository
//			persone.add(p);
//		}
//		return persone;
//	}
//	
//	
//	
//	//per verificare i duplicati in persona Validator
//	//ho bisogno di un metodo in repository
//	
//	
//	//
//	
//	public boolean alreadyExist(Credenziali cred) {
////		pr.existsByNomeAndCognome(persona.getNome(), persona.getCognome());
////		return ur.existsByNomeAndCognomeAndRuolo(utente.getNome(), utente.getCognome(), utente.getRuolo());
//		return ur.existsByUserName(cred.getUsername());
//	}
//	
//	
//	
//	/* Utilizzando questa annotazione ci pensa Sping boot ad aprire e chiudere la transazione*/
//	@Transactional
//	public void deleteById(Long id) {
//		ur.deleteById(id);
//	}
	
}
