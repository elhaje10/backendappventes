package com.diallo.restfull.applicationventes.api;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class AuthRequestResetPassword {
	   
	   @NotNull @Email @Length(min = 5, max = 50)
	    private String email;
	   
	    	
	   @NotNull @Length(min = 5, max = 64, message = "the password should at least 5 characters")
	    private String password; 
	   
	   @NotNull @Length(min = 5, max = 64, message = "the password should at least 5 characters")
	    private String cpassword; 
	   
	   
	   
	    public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

		public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
