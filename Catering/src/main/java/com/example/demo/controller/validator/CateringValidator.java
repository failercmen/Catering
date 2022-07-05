package com.example.demo.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Catering;

@Component
public class CateringValidator implements Validator {

	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;

	@Override
	public boolean supports(Class<?> pclass) {
		return Catering.class.equals(pclass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Catering chef = (Catering) target;
		String nome = chef.getNome().trim();

		if (nome.isEmpty())
			errors.rejectValue("nome", "required");
		else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
			errors.rejectValue("nome", "size");

	}

}
