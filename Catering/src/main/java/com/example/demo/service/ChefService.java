package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	private ChefRepository chefRepository; 
	 
	
	@Transactional
	public Chef inserisci(Chef chef) {
		return chefRepository.save(chef);
	}

	@Transactional
	public List<Chef> tutti() {
		return (List<Chef>) chefRepository.findAll();
	}

	@Transactional
	public Chef chefPerId(Long id) {
		Optional<Chef> optional = chefRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}
	

	@Transactional
	public boolean alreadyExists(Chef chef) {
		List<Chef> prodotti = this.chefRepository.findByNomeAndCognome(chef.getNome(),chef.getCognome());
		if (prodotti.size() > 0)
			return true;
		else 
			return false;
	}
	
	@Transactional
	public void deleteById(Long id) {
	 	chefRepository.deleteById(id);
	}
	
	@Transactional
	public List<Buffet> tuttiBuffetDiUnoChef(Long id) {
	 	List<Buffet> tuttiBuffet = this.chefPerId(id).getListaBuffet();
	 	return tuttiBuffet;
	}
	
}
