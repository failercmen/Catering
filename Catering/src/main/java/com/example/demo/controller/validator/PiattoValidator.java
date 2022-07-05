package com.example.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Piatto;
import com.example.demo.service.PiattoService;

@Component
public class PiattoValidator implements Validator  {
	
	@Autowired
	private PiattoService piattoService;

	final Integer MAX_NAME_LENGTH = 50;
	final Integer MIN_NAME_LENGTH = 2;
	final Integer MAX_DESCRIZIONE_LENGTH = 100;
	final Integer MIN_DESCRIZIONE_LENGTH = 2;
	
	@Override
	public boolean supports(Class<?> pclass) {
		return Piatto.class.equals(pclass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Piatto piatto = (Piatto)target;
		String nome = piatto.getNome().trim();
		String descrizione = piatto.getDescrizione().trim();
		

		if (nome.isEmpty())
			errors.rejectValue("nome", "required");
		else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
			errors.rejectValue("nome", "size");
		else if (this.piattoService.alreadyExists(piatto))
			errors.rejectValue("nome", "duplicate");
		
		if (descrizione.isEmpty())
			errors.rejectValue("descrizione", "required");
		else if (descrizione.length() < MIN_DESCRIZIONE_LENGTH || descrizione.length() > MAX_DESCRIZIONE_LENGTH)
			errors.rejectValue("descrizione", "size");
		
	}
}
