package com.example.demo.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.model.Buffet;
import com.example.demo.model.Utente;
import com.example.demo.service.BuffetService;


@Component
public class BuffetValidator implements Validator{

	@Autowired
	private BuffetService buffetService;
	
//    private static final Logger logger = LoggerFactory.getLogger(BuffetService.class);
	final Integer MAX_NAME_LENGTH = 20;
	final Integer MIN_NAME_LENGTH = 2;
	final Integer MAX_DESCRIZIONE_LENGTH = 50;
	final Integer MIN_DESCRIZIONE_LENGTH = 2;
	
    
//	@Override
//	public void validate(Object o, Errors errors) {
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione", "required");
//
//		if (!errors.hasErrors()) {
//			logger.debug("confermato: valori non nulli");
//			if (this.buffetService.alreadyExists((Buffet)o)) {
//				logger.debug("e' un duplicato");
//				errors.reject("duplicato");
//			}
//		}
//	}
    

	@Override
	public void validate(Object target, Errors errors) {
		Buffet buffet = (Buffet)target;
		String nome = buffet.getNome().trim();
		String descrizione = buffet.getDescrizione().trim();
		
		

		if (nome.isEmpty())
			errors.rejectValue("nome", "required");
		else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
			errors.rejectValue("nome", "size");
		else if (this.buffetService.alreadyExists(buffet))
			errors.rejectValue("nome", "duplicate");
		

		if (descrizione.isEmpty())
			errors.rejectValue("descrizione", "required");
		else if (descrizione.length() < MIN_DESCRIZIONE_LENGTH || descrizione.length() > MAX_DESCRIZIONE_LENGTH)
			errors.rejectValue("descrizione", "size");


	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Buffet.class.equals(aClass);
	}
    
}
