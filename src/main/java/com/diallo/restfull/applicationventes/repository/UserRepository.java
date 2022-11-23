package com.diallo.restfull.applicationventes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diallo.restfull.applicationventes.model.User;





@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	
	Optional<User> findByemail (String email);

}
