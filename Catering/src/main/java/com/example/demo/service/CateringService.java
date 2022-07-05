package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Catering;
import com.example.demo.repository.CateringRepository;

@Service
public class CateringService  {


	@Autowired
	private CateringRepository cateringRepository; 
	
	@Transactional
	public Catering inserisci(Catering catering) {
		return cateringRepository.save(catering);
	}

	@Transactional
	public List<Catering> tutti() {
		return (List<Catering>) cateringRepository.findAll();
	}

	@Transactional
	public Catering prodottoPerId(Long id) {
		Optional<Catering> optional = cateringRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		else 
			return null;
	}

	@Transactional
	public boolean alreadyExists(Catering catering) {
		List<Catering> prodotti = this.cateringRepository.findByNome(catering.getNome());
		if (prodotti.size() > 0)
			return true;
		else 
			return false;
	}
	
}
