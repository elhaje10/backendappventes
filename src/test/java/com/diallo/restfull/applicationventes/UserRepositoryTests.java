package com.diallo.restfull.applicationventes;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.diallo.restfull.applicationventes.model.User;
import com.diallo.restfull.applicationventes.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired private UserRepository repo;
    
    @Test
    public void testCreateUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("lindor");
         
        User newUser = new User("ldiallo@hotmail.com", password, "aaaaaa", "aaaaaaaaa");
        User savedUser = repo.save(newUser);
        
       System.out.println( repo.findById(newUser.getId()));
         
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }
    
    
    @Test
    public void findUser() {
    	System.out.println( repo.findByemail("nam@codejava.net"));
    }

}
