package com.rec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rec.dao.UserRepository;
import com.rec.entity.User;

@Service
public class UserService {

	@Autowired
	private UserRepository dao;

	public User saveorUpdate(User user) {
		User existing = dao.findByUsername(user.getUsername());

		if (existing != null) {
			if (user.getId() == 0 || existing.getId() != user.getId()) {
				throw new RuntimeException("Username already exists");
			}
		}

		return dao.save(user);
	}

	public User loginUser(String username, String password) {
		return dao.findByUsernameAndPassword(username, password);
	}

	public List<User> getAllUser() {
		List<User> allUser = dao.findAll();
		return allUser;
	}

	public void deleteById(int id) {
		dao.deleteById(id);

	}

	public User getUserById(int id) {
		return dao.findById(id).orElse(null);
	}

	public Optional<User> getUserByIdOptional(int id) {
		return dao.findById(id);
	}

	public User findByEmail(String email) {
		return dao.findAll().stream().filter(u -> email != null && email.equalsIgnoreCase(u.getEmail())).findFirst()
				.orElse(null);
	}

	public void updatePassword(User user, String newPassword) {
		user.setPassword(newPassword); // hash here in real app
		dao.save(user);
	}

}
