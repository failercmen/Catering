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

import com.example.demo.controller.validator.ChefValidator;
import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.service.ChefService;

@Controller
public class ChefController {
	
	@Autowired
	private ChefService chefService;
	
    @Autowired
    private ChefValidator chefValidator;
    
    
    @RequestMapping(value = "/chef", method = RequestMethod.GET)
    public String getListaChef(Model model) {
    		model.addAttribute("listaChef", this.chefService.tutti());
    		return "listaChef";
    }
    
    @RequestMapping(value = "/chefUser", method = RequestMethod.GET)
    public String getListaChefUser(Model model) {
    		model.addAttribute("listaChef", this.chefService.tutti());
    		return "listaChefUser";
    }
    
    
    @RequestMapping(value = "/admin/chef", method = RequestMethod.GET)
    public String addChef(Model model) {
    	model.addAttribute("chef", new Chef());
        return "chefForm";
    }
    
    @RequestMapping(value = "/admin/chef", method = RequestMethod.POST)
    public String addChef(@ModelAttribute("chef") Chef chef, Model model, BindingResult bindingResult) {
    	this.chefValidator.validate(chef, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.chefService.inserisci(chef);
            model.addAttribute("listaChef", this.chefService.tutti());
            return "listaChef";
        }
        return "chefForm";
    }
    
    @RequestMapping(value = "/chef/{id}", method = RequestMethod.GET)
    public String getChef(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("chef", this.chefService.chefPerId(id));
    	return "chef";
    }
    
    @Transactional
	@GetMapping("/deleteChef/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
    	
    	for(Buffet b:chefService.tuttiBuffetDiUnoChef(id)) {
    		b.setChef(null);
    	}
    	
		chefService.deleteById(id); //cancellazione piatto
		model.addAttribute("listaChef", this.chefService.tutti());
		return "listaChef";
	}
   
  

}
