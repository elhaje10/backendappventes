package com.diallo.restfull.applicationventes.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Cache.Cachecontrol;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.diallo.restfull.applicationventes.jwt.JwtTokenFilter;
import com.diallo.restfull.applicationventes.repository.UserRepository;



@EnableWebSecurity(debug = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired UserRepository repository;
	@Autowired JwtTokenFilter jwtTokenFilter;
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }  
    
    

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(
				username -> repository.findByemail(username)
				.orElseThrow(
						() -> new UsernameNotFoundException("Username "+username + " not found")));
			
	}


	@Bean
	@Override 
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.cors().and();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
    		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
        //, "/error/**"
    		.antMatchers("/auth/adduser", "/products","/users/forgetpassword/{email}").permitAll()
        	.antMatchers("/auth/login").permitAll()
        	.anyRequest().authenticated();
        http.exceptionHandling()
        .authenticationEntryPoint(
            (request, response, ex) -> {
                response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    ex.getMessage()
                );
            }
        );
 
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers();
     

    }
	

	
	
}
