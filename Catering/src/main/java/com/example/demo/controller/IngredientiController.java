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

import com.example.demo.controller.validator.IngredienteValidator;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.IngredienteService;

@Controller
public class IngredientiController {

	
	@Autowired
	private IngredienteService ingredientiService;
	
	@Autowired
	private IngredienteValidator ingredientiValidator;
	
	
	
    @RequestMapping(value = "/ingrediente", method = RequestMethod.GET)
    public String getListaBuffet(Model model) {
    		model.addAttribute("listaIngredienti", this.ingredientiService.tutti());
    		return "listaIngredienti";
    }
    
    @RequestMapping(value = "/ingredienteUser", method = RequestMethod.GET)
    public String getListaBuffetUser(Model model) {
    		model.addAttribute("listaIngredienti", this.ingredientiService.tutti());
    		return "listaIngredientiUser";
    }
    
    @RequestMapping(value = "/admin/ingrediente", method = RequestMethod.GET)
    public String addChef(Model model) {
    	model.addAttribute("ingrediente", new Ingrediente());
        return "ingredienteForm";
    }
    
    
    @RequestMapping(value = "/admin/ingrediente", method = RequestMethod.POST)
    public String addChef(@ModelAttribute("ingrediente") Ingrediente ingrediente, 
    									Model model, BindingResult bindingResult) {
    	this.ingredientiValidator.validate(ingrediente, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.ingredientiService.inserisci(ingrediente);
            model.addAttribute("listaIngredienti", this.ingredientiService.tutti());
            return "listaIngredienti";
        }
        return "ingredienteForm";
    }
    
    @RequestMapping(value = "/ingrediente/{id}", method = RequestMethod.GET)
    public String getPersona(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("ingrediente", this.ingredientiService.ingredientePerId(id));
    	return "ingrediente";
    }
    
    @Transactional
	@GetMapping("/deleteIngrediente/{id}")
	public String deleteIngrediente(@PathVariable("id") Long id, Model model) {
    	
    	Ingrediente i = ingredientiService.ingredientePerId(id);
    	
    		
    		//usato per eliminare l'ingrediente da ogni piatto in cui è presente
    	for(Piatto p : ingredientiService.piattiDiIngredienti(i)) {
//    			p.removeIngrediente(i);
    		p.getListaIngredienti().remove(i);
    		}
    	//elimino prima da tutti i piatti l'ingrediente
    	 	
		ingredientiService.deleteById(id); //cancellazione ingrediente
 		model.addAttribute("listaIngredienti", this.ingredientiService.tutti());
		return "listaIngredienti";
    }
}

    
	

