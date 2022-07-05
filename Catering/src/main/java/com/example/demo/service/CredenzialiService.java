package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Credenziali;
import com.example.demo.repository.CredenzialiRepository;

@Service
public class CredenzialiService {

	    @Autowired
	    protected PasswordEncoder passwordEncoder;

		@Autowired
		protected CredenzialiRepository credentialsRepository;
		
		@Transactional
		public Credenziali getCredentials(Long id) {
			Optional<Credenziali> result = this.credentialsRepository.findById(id);
			return result.orElse(null);
		}

		@Transactional
		public Credenziali getCredentials(String username) {
			Optional<Credenziali> result = this.credentialsRepository.findByUsername(username);
			return result.orElse(null);
		}
			
	    @Transactional
	    public Credenziali saveCredentials(Credenziali credentials) {
//	        credentials.setRuolo(Credenziali.DEFAULT_ROLE);
	        credentials.setRuolo(Credenziali.ADMIN_ROLE);
	        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
	        return this.credentialsRepository.save(credentials);
	    }
}
