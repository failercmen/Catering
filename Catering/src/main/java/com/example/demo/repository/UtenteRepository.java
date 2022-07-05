package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Utente;



/* Creo sempre un interfaccia repository nella quale avrò metodi del tipo findById ecc*/
/* per creare questa classe faccio una new interfaccia e faccio add crudRepository*/

/* All'interfaccia è possibile aggiungere i metodi find, delete, count, exist specifici se ne abbiamo bisogno.
 * 
 * Una volta aggiunti i metodi li andiamo a definire in persona service che verrà utilizzata dal validator per
 * eventuali ricerche e cancellazioni
 *
 * */

/*estendendo crudRepository avrò diversi metodi a dispozione, basta fare ctrl+spazio*/
public interface UtenteRepository extends CrudRepository<Utente,Long> {
	
//	public boolean existsByNomeAndCognomeAndRuolo(String nome, String cognome, String ruolo);
	
//	public boolean existsByUserName(String userName);

	
//	public boolean existsByNomeAndCognome(String nome, String cognome);
	
	//public void deleteById(Long id); CE LO OFFRE GIà LA CRUDREPOSITORY
	
}
