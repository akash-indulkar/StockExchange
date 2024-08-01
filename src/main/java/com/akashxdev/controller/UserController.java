package com.akashxdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.akashxdev.model.User;
import com.akashxdev.service.JWTService;
import com.akashxdev.service.MyUserDetailsService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private MyUserDetailsService userService;
	
	@Autowired
	private JWTService jwtService;

	@Autowired
	AuthenticationManager authManager;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User user) {
		return userService.add(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		if(auth.isAuthenticated()) {
			String token = jwtService.generateToken(user.getEmail());
			return new ResponseEntity<String>(token, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Login Failed!", HttpStatus.FORBIDDEN);
	}
	
	@PostMapping("/addbalance")
	public ResponseEntity<String> addBalance(@RequestParam Integer userID, @RequestParam Integer balance) {
		return userService.addBalance(userID, balance);
	}
	
	@GetMapping("/getBalance/{userID}")
	public ResponseEntity<?> getBalance(@PathVariable Integer userID) {
		return userService.getBalance(userID);
	}
	
	
	
}
