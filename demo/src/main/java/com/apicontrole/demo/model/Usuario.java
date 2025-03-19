package com.apicontrole.demo.model;

public class Usuario {

    private String name; // Alterado 'nome' para 'name'
    private String email;
    private String password;

    // Construtor
    public Usuario(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters e Setters
    public String getNome() { // Mantido como 'getNome()'
        return name;
    }

    public void setNome(String nome) { // Mantido como 'setNome()'
        this.name = nome;
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
