package com.diallo.restfull.applicationventes.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diallo.restfull.applicationventes.api.AuthRequest;
import com.diallo.restfull.applicationventes.api.AuthRequestResetPassword;
import com.diallo.restfull.applicationventes.api.AuthResponse;
import com.diallo.restfull.applicationventes.jwt.JwtTokenUtil;
import com.diallo.restfull.applicationventes.model.Product;
import com.diallo.restfull.applicationventes.model.User;
import com.diallo.restfull.applicationventes.repository.UserRepository;


@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthApi {
	
	@Autowired AuthenticationManager authenticationManager;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired JwtTokenUtil jwtUtil;
	
	@Autowired UserRepository repo;
	
	@GetMapping(path = "/users")
	public  List<User> list(){
		return repo.findAll();
	}
	
	@GetMapping(path = "/users/{email}")
	public  Optional<User> getUser(@PathVariable String email){
		return repo.findByemail(email);
	}
	
	
	@PutMapping("/users/forgetpassword/{email}")
	public User forgetPassword(@PathVariable String email,  @RequestBody @Valid   AuthRequest authRequest ) throws Exception {
		 return repo.findByemail(email).map(user -> {
			user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
			return repo.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("user "+ email + "not found"));				
	}
	
	@PutMapping("/users/{userId}")
	public User updateUser(@PathVariable Integer userId,@RequestBody @Valid   User user_01 ) throws Exception {
		 return repo.findById(userId).map(user -> {
			user.setEmail(user_01.getEmail());
			user.setPassword(passwordEncoder.encode(user_01.getPassword()));
			user.setFirstname(user_01.getFirstname());
			user.setLastname(user_01.getLastname());
			return repo.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("user "+ userId + "not found"));				
	}
	
    @PostMapping("/auth/adduser")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
      
	    try {
	        User userrequest = new User ();
	        userrequest.setPassword(passwordEncoder.encode(user.getPassword()));
	        userrequest.setEmail(user.getEmail());
	        userrequest.setLastname(user.getLastname());
	       userrequest.setFirstname(user.getFirstname());;
			User saveUser = repo.save(userrequest);
			URI userURI = URI.create("/users/" + saveUser.getId());
	        return ResponseEntity.created(userURI).body(saveUser);
			} catch (Exception e) {
				throw new ResourceNotFoundException("There is an account with this email adress:" + user.getEmail() + " try to connect or change password");
		}
    	
    }
	
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer userId) throws Exception {
        return repo.findById(userId).map(post -> {
            repo.delete(post);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("user "+ userId + "not found"));
    }
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody @Valid   AuthRequest authRequest){
		
		try {
			 Authentication authentication = 	authenticationManager.authenticate(
					 new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
					 );
			 
			 User user = (User) authentication.getPrincipal();
			String token = jwtUtil.generateAccessToken(user);
	            AuthResponse response = new AuthResponse(user.getId(),user.getEmail(), token);
	            
	            return ResponseEntity.ok().body(response);
			 
			 
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	
	
	

}
