package com.rec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rec.dao.PersonRepository;
import com.rec.entity.Person;

@Service
public class PersonService {

	@Autowired
	private PersonRepository dao;

	public boolean saveorUpdateCriminal(Person person) {
		if (dao.existsByName(person.getName())) {
			return false;
		}
		dao.save(person);
		return true;
	}
	
	public Person createPerson(Person person) {
        // if you want to reject duplicate names
        if (dao.existsByName(person.getName())) {
            throw new RuntimeException("Name already exists");
        }

        // createdAt is set automatically because of @CreationTimestamp [web:81][web:94]
        return dao.save(person);
    }

	public Person getPersonById(Long id) {
		return dao.findById(id).orElse(null);
	}

	// ✅ Add this for better latest person reliability
	public Person getLatestPerson() {
		return dao.findTopByOrderByIdDesc(); // More reliable than createdAt
	}

	public List<Person> getAllUser() {
		List<Person> allPersons = dao.findAll();
		return allPersons;
	}

	public void deleteById(int id) {
		dao.deleteById(id);

	}

	public Person getUserById(int id) {
		Person person = dao.findById(id).get();
		return person;
	}

}
