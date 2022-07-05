package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Credenziali;


public interface CredenzialiRepository extends CrudRepository<Credenziali, Long> {
	
	public Optional<Credenziali> findByUsername(String username);

}