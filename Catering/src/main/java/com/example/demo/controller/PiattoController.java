package com.example.demo.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.controller.validator.PiattoValidator;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;

@Controller
public class PiattoController {

	@Autowired
	private PiattoService piattoService;

	@Autowired
	private PiattoValidator piattoValidator;

	@Autowired
	private IngredienteService ingredienteService;

	@RequestMapping(value = "/piatto", method = RequestMethod.GET)
	public String getListaPiatti(Model model) {
		model.addAttribute("listaPiatti", this.piattoService.tutti());
		return "listaPiatti";
	}
	
	
	@RequestMapping(value = "/piattoUser", method = RequestMethod.GET)
	public String getListaPiattiUser(Model model) {
		model.addAttribute("listaPiatti", this.piattoService.tutti());
		return "listaPiattiUser";
	}

	@RequestMapping(value = "/admin/piatto", method = RequestMethod.GET)
	public String addPiatto(Model model) {
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("listaIngredienti", this.ingredienteService.tutti());
		return "piattoForm";
	}

	@RequestMapping(value = "/admin/piatto", method = RequestMethod.POST)
	public String addPiatto(@ModelAttribute("piatto") Piatto piatto, Model model, BindingResult bindingResult) {
		this.piattoValidator.validate(piatto, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.piattoService.inserisci(piatto);
			model.addAttribute("listaPiatti", this.piattoService.tutti());
			model.addAttribute("listaIngredienti", this.ingredienteService.tutti());
			return "listaPiatti";
		}
		model.addAttribute("listaPiatti", this.piattoService.tutti());
		model.addAttribute("listaIngredienti", this.ingredienteService.tutti());
		return "piattoForm";
	}

	// //usato per la stampa dei piatti
	// @RequestMapping(value = "/piatto/{id}", method = RequestMethod.GET)
	// public String getPiatto(@PathVariable("id") Long id, Model model) {
	// model.addAttribute("piatto", this.piattoService.piattoPerId(id));
	// return "piatto";
	// }

	// usato per la stampa dei piatti
	@RequestMapping(value = "/piatto/{id}", method = RequestMethod.GET)
	public String getPiatto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", this.piattoService.piattoPerId(id));
		model.addAttribute("listaIngredienti", this.piattoService.ingredientiPiatto(id));
		return "piatto";
	}

	@Transactional
	@GetMapping("/deletePiatto/{id}")
	public String deletePiatto(@PathVariable("id") Long id, Model model) {
		piattoService.deleteById(id); // cancellazione piatto
		model.addAttribute("listaPiatti", piattoService.tutti()); // prendo l'elenco dei piatti
		model.addAttribute("listaIngredienti", this.ingredienteService.tutti());
		return "listaPiatti"; // rimostro l'elenco dei piatti
	}

	@Transactional
	@GetMapping("/deleteIngredienteDaPiatto/{idPiatto}/{idIngrediente}")
	// primo id: id piatto, secondo id: id piatto ingrediente
	public String deleteIngredienteDaPiatto(@PathVariable("idPiatto") Long idPiatto,
			@PathVariable("idIngrediente") Long idIngrediente, Model model) {

		Piatto piatto = piattoService.piattoPerId(idPiatto);
		Ingrediente ingrediente = ingredienteService.ingredientePerId(idIngrediente);
		piatto.getListaIngredienti().remove(ingrediente);
		
//		this.piattoService.eliminaIngredienteDaPiatto(piattoService.piattoPerId(idPiatto),
//				ingredienteService.ingredientePerId(idIngrediente));

		model.addAttribute("piatto", this.piattoService.piattoPerId(idPiatto));
		model.addAttribute("listaIngredienti", this.piattoService.ingredientiPiatto(idPiatto));

		return "piatto";
	}
	
	

}
