package com.rec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rec.dto.ForgotPasswordRequest;
import com.rec.dto.LoginResponse;
import com.rec.dto.ResetPasswordRequest;
import com.rec.dto.VerifyOtpRequest;
import com.rec.entity.User;
import com.rec.service.EmailService;
import com.rec.service.OtpService;
import com.rec.service.UserService;

@CrossOrigin(origins = "http://localhost:5173")

@Controller
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private EmailService emailService;

	@Autowired
	private OtpService otpService;

	@PostMapping("/register-user")
	public ResponseEntity<Boolean> registerUser(@RequestBody String jsonPayload) {
		try {
			User user = objectMapper.readValue(jsonPayload, User.class);
			boolean success = service.saveorUpdate(user) != null;
			return ResponseEntity.ok(success);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(false);
		}
	}

	@PostMapping("/register-users")
	public ResponseEntity<?> registerUsers(@RequestBody User user) {
		try {
			User saved = service.saveorUpdate(user);
			// Return 201 CREATED on success
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} catch (RuntimeException ex) {
			// Username already exists
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
		}
	}

//	@PostMapping("/login-user")
//	@ResponseBody
//	public ResponseEntity<LoginResponse> loginUser(@RequestBody User user) {
//
//		// Make sure: UserService.loginUser returns User
//		User authenticatedUser = service.loginUser(user.getUsername(), user.getPassword());
//
//		if (authenticatedUser != null) {
//			LoginResponse response = new LoginResponse(true, authenticatedUser.getUsername(), "Login successful");
//			return ResponseEntity.ok(response);
//		}
//
//		LoginResponse response = new LoginResponse(false, null, "Invalid credentials");
//		return ResponseEntity.badRequest().body(response);
//	}

	@PostMapping("/login-user")
	@ResponseBody
	public ResponseEntity<LoginResponse> loginUser(@RequestBody User user) {

		User authenticatedUser = service.loginUser(user.getUsername(), user.getPassword());

		if (authenticatedUser != null) {
			LoginResponse response = new LoginResponse(true, authenticatedUser.getUsername(), "Login successful",
					authenticatedUser.getRole() // <-- here
			);
			return ResponseEntity.ok(response);
		}

		LoginResponse response = new LoginResponse(false, null, "Invalid credentials", null);
		return ResponseEntity.badRequest().body(response);
	}

	@GetMapping("/get-all-users")
	@ResponseBody
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = service.getAllUser();
		return ResponseEntity.ok(users);
	}

	@RequestMapping("/show-user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable int id) {
		try {
			User user = service.getUserById(id);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/update/{id}")
	@ResponseBody
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User incoming) {
		User existing = service.getUserById(id);

		existing.setUsername(incoming.getUsername());
		existing.setEmail(incoming.getEmail());
		existing.setRole(incoming.getRole());
		if (incoming.getPassword() != null && !incoming.getPassword().isEmpty()) {
			existing.setPassword(incoming.getPassword());
		}
		// ⚠️ do NOT call existing.setCreatedAt(...)

		User updatedUser = service.saveorUpdate(existing);
		return ResponseEntity.ok(updatedUser); // will include existing.createdAt
	}

	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public ResponseEntity<Void> deleteUser(@PathVariable int id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	// ... existing endpoints remain ...

	@PostMapping("/forgot-password")
	@ResponseBody
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest req) {
		User user = service.findByEmail(req.getEmail());
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not registered");
		}

		String otp = otpService.generateOtp(req.getEmail(), 60); // 60s TTL [web:44][web:52]
		emailService.sendOtpEmail(req.getEmail(), otp);

		return ResponseEntity.ok("OTP sent to email");
	}

	@PostMapping("/verify-otp")
	@ResponseBody
	public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest req) {
		boolean valid = otpService.validateOtp(req.getEmail(), req.getOtp());
		if (valid) {
			return ResponseEntity.ok("OTP valid");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP");
	}

	@PostMapping("/reset-password")
	@ResponseBody
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {
		boolean valid = otpService.validateOtp(req.getEmail(), req.getOtp());
		if (!valid) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP");
		}

		User user = service.findByEmail(req.getEmail());
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not registered");
		}

		service.updatePassword(user, req.getNewPassword());
		return ResponseEntity.ok("Password updated successfully");
	}

}
