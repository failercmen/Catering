package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Catering;

public interface CateringRepository extends CrudRepository<Catering, Long> {
	
	public List<Catering> findByNome(String nome);
}
