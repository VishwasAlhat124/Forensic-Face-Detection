package com.rec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rec.entity.Person;
import com.rec.service.PersonService;

@CrossOrigin(origins = "http://localhost:5173")

@Controller
public class PersonController {

	@Autowired
	private PersonService service;

	@RequestMapping("/update-person")
	public String updateUser(@ModelAttribute Person person, Model model) {

		service.saveorUpdateCriminal(person);
		List<Person> allUsers = service.getAllUser();
		model.addAttribute("users", allUsers);
		return "update-user";
	}

//	@PostMapping("/register-person")
//	public ResponseEntity<Boolean> registerPerson(@RequestBody Person person) {
//	    boolean success = service.saveorUpdateCriminal(person);
//	    return ResponseEntity.ok(success);
//	}

//	@PostMapping("/register-person")
//	public ResponseEntity<Boolean> registerPerson(@RequestBody Person person) {
//		try {
//			boolean success = service.saveorUpdateCriminal(person);
//			return ResponseEntity.ok(success);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
//		}
//	}

	@PostMapping("/register-person")
	public ResponseEntity<?> registerPerson(@RequestBody Person person) {
		try {
			Person saved = service.createPerson(person);
			// 201 CREATED with saved entity in body [web:87][web:52]
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} catch (RuntimeException ex) {
			// duplicate name
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Name already exists");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
		}
	}


	@GetMapping("/latest-person")
	public ResponseEntity<Person> getLatestPerson() {
		Person person = service.getLatestPerson();
		if (person == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(person);
	}

}
