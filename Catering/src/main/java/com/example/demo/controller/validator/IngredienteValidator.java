package com.example.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Ingrediente;
import com.example.demo.service.IngredienteService;

@Component
public class IngredienteValidator implements Validator {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;
	final Integer MAX_DESCRIZIONE_LENGTH = 100;
	final Integer MIN_DESCRIZIONE_LENGTH = 2;

	@Override
	public boolean supports(Class<?> pclass) {
		return Ingrediente.class.equals(pclass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ingrediente ingrediente = (Ingrediente)target;
		String nome = ingrediente.getNome().trim();
		String origine = ingrediente.getOrigine().trim();
		String descrizione = ingrediente.getDescrizione().trim();
		

		if (nome.isEmpty())
			errors.rejectValue("nome", "required");
		else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
			errors.rejectValue("nome", "size");
		else if (this.ingredienteService.alreadyExists(ingrediente))
			errors.rejectValue("nome", "duplicate");
		

		if (origine.isEmpty())
			errors.rejectValue("origine", "required");
		else if (origine.length() < MIN_NAME_LENGTH || origine.length() > MAX_NAME_LENGTH)
			errors.rejectValue("origine", "size");
		
		
		if (descrizione.isEmpty())
			errors.rejectValue("descrizione", "required");
		else if (nome.length() < MIN_DESCRIZIONE_LENGTH || nome.length() > MAX_DESCRIZIONE_LENGTH)
			errors.rejectValue("descrizione", "size");
	}
	
	

}
