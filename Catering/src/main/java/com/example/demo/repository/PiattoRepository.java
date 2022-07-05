package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {

	public List<Piatto> findByNome(String nome);
}
