package com.mycompany.app.dto;

public class LoginResponseDTO {

    private Long id;
    private String email;
    private String roles;

    public LoginResponseDTO(Long id, String email, String roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}

