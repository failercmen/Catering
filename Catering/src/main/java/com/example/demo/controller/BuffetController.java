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

import com.example.demo.controller.validator.BuffetValidator;
import com.example.demo.model.Buffet;
import com.example.demo.model.Piatto;
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.service.PiattoService;

@Controller
public class BuffetController {

	@Autowired
	private BuffetService buffetService;

	@Autowired
	private BuffetValidator buffetValidator;

	@Autowired
	private ChefService chefService;

	@Autowired
	private PiattoService piattoService;

	@RequestMapping(value = "/buffet", method = RequestMethod.GET)
	public String getListaBuffet(Model model) {
		model.addAttribute("listaBuffet", this.buffetService.tutti());
		return "listaBuffet";
	}
	
	@RequestMapping(value = "/buffetUser", method = RequestMethod.GET)
	public String getListaBuffetUser(Model model) {
		model.addAttribute("listaBuffet", this.buffetService.tutti());
		return "listaBuffetUser";
	}

	@RequestMapping(value = "/admin/buffet", method = RequestMethod.GET)
	public String addBuffet(Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("listaCuochi", this.chefService.tutti());
		model.addAttribute("listaPiatti", this.piattoService.tutti());
		return "buffetForm";
	}

	// nota se aggiungi anch il piatto ricorda di dire a quale buffet si riferisce

	@RequestMapping(value = "/admin/buffet", method = RequestMethod.POST)
	public String addBuffet(@ModelAttribute("buffet") Buffet buffet, Model model, BindingResult bindingResult) {
		this.buffetValidator.validate(buffet, bindingResult);
		if (!bindingResult.hasErrors()) {

			// ogni volta che aggiungo un buffet,
			// collego il buffet al piatto (questo dovuto al collegamento bidirezionale)
			for (Piatto p : buffet.getListaPiatti()) {
				p.getListaBuffet().add(buffet);
			}

			this.buffetService.inserisci(buffet);
			model.addAttribute("listaBuffet", this.buffetService.tutti());
			model.addAttribute("listaPiatti", this.piattoService.tutti());
			model.addAttribute("listaCuochi", this.chefService.tutti());
			return "listaBuffet";
		}
		model.addAttribute("listaBuffet", this.buffetService.tutti());
		model.addAttribute("listaPiatti", this.piattoService.tutti());
		model.addAttribute("listaCuochi", this.chefService.tutti());
		return "buffetForm";
	}

	// qui visualizzo il buffet tramite la listaBuffet
	@RequestMapping(value = "/buffet/{id}", method = RequestMethod.GET)
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.buffetPerId(id));
		model.addAttribute("listaPiattiBuffet", this.buffetService.piattiBuffet(id));
		return "buffet";
	}

	@Transactional
	@GetMapping("/deleteBuffet/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {

		// NOTA TODO vedi se eliminare (apparentemente funziona)
		// prima vedi tutti i piatti a cui è associata e togli il riferimento
		for (Piatto p : buffetService.buffetPerId(id).getListaPiatti()) {
			p.getListaBuffet().remove(buffetService.buffetPerId(id));
		}

		buffetService.deleteById(id); // cancellazione piatto
		model.addAttribute("listaBuffet", this.buffetService.tutti());
		model.addAttribute("listaPiatti", this.piattoService.tutti());
		return "listaBuffet"; // rimostro l'elenco dei piatti
	}

	
	@Transactional
	@GetMapping("/deletePiattoDaBuffet/{idBuffet}/{idPiatto}")
	// primo id: id piatto, secondo id: id piatto ingrediente
	public String deletePiattoDaBuffet(@PathVariable("idBuffet") Long idBuffet, @PathVariable("idPiatto") Long idPiatto,
			Model model) {

		Buffet buffet = buffetService.buffetPerId(idBuffet);
		 Piatto piatto = piattoService.piattoPerId(idPiatto);
		
		Buffet b = piattoService.buffetCorrispondente(piattoService.piattoPerId(idPiatto), idBuffet);
		piatto.getListaBuffet().remove(b);
		
		// rispetto a piatto con ingredienti si prova a fare
		// tutto nel controller
		piatto.getListaBuffet().remove(b);
		 buffet.getListaPiatti().remove(piatto);

		 //vecchio non funzionante
//		this.buffetService.eliminaPiattoDaBuffet(buffetService.buffetPerId(idBuffet),
//				piattoService.piattoPerId(idPiatto));

		model.addAttribute("buffet", this.buffetService.buffetPerId(idBuffet));
		model.addAttribute("listaPiattiBuffet", this.buffetService.piattiBuffet(idBuffet));

		return "buffet";
	}
	
	
	
	

	

	// QUI TI FAI DARE LA LISTA DEI BUFFET E SI SCEGLIE IL BUFFET DA MODIFICARE
	// usato per visualizzare il buffet che di cui si vuole modificare il cuoco
	@RequestMapping(value = "/updateBuffetLista", method = RequestMethod.GET)
	public String updateCuocoBuffetLista(Model model) {
		model.addAttribute("listaBuffet", this.buffetService.tutti());
		return "buffetUpdateList.html";
	}

	//vecchio (FUNZIONA)
	@RequestMapping(value = "/updateBuffet/{idBuffet}", method = RequestMethod.GET)
	public String updateCuocoBuffet(@PathVariable("idBuffet") Long idBuffet, Model model) {

		model.addAttribute("buffet", this.buffetService.buffetPerId(idBuffet));
		model.addAttribute("listaCuochi", this.chefService.tutti());
		model.addAttribute("listaPiatti", this.piattoService.piattiNonPresenti(this.buffetService.buffetPerId(idBuffet)));
		return "buffetUpdate.html";
	}

	//nuovo get
//	@RequestMapping(value = "/updateBuffet/{idBuffet}", method = RequestMethod.GET)
//	public String updateCuocoBuffet(@PathVariable("idBuffet") Long idBuffet, Model model) {
//
//		model.addAttribute("buffetVecchio", this.buffetService.buffetPerId(idBuffet));
//		model.addAttribute("buffet", new Buffet());
//		model.addAttribute("listaCuochi", this.chefService.tutti());
//		model.addAttribute("listaPiatti", this.piattoService.tutti());
//		return "buffetUpdate.html";
//	}
	
	

	//NON CAMBIA ID COME IL RPECEDENTE MA non modifica ancora i piatti
	@RequestMapping(value = "/updateBuffet/{idBuffet}", method =
			RequestMethod.POST)
	public String updateCuocoBuffet(@ModelAttribute Buffet buffet,@PathVariable("idBuffet")
	Long idBuffet,Model model) {
	//TODO ricorda di aggiungere il collegamento verso il buffet
							
		//lascialo
		this.buffetService.updateBuffet(idBuffet, buffet);
				
		
		model.addAttribute("piattiBuffet",this.buffetService.buffetPerId(idBuffet).getListaPiatti());
		model.addAttribute("listaBuffet", this.buffetService.tutti());
		model.addAttribute("listaCuochi", this.chefService.tutti());
		model.addAttribute("listaPiatti", this.piattoService.tutti());
		
		return "listaBuffet.html";
	}
	

}
