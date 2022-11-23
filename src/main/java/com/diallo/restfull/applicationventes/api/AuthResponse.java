package com.diallo.restfull.applicationventes.api;

public class AuthResponse {
	
	
	private Integer id;	
	private String email;
    private String token;
 
    public AuthResponse() { }
     
    public AuthResponse(Integer id, String email, String token) {
    	this.id = id;
        this.email = email;
        this.token = token;
    }

    public Integer getId() {
 		return id;
 	}

 	public void setId(Integer id) {
 		this.id = id;
 	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String token) {
		this.token = token;
	}
    
}
