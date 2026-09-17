package com.asesorespecausa.system.model;


public class User {
    
    private String name;
    private String password;
    private char idUser;
    
    public User(){}
    
       public User(String name, String password,char idUser) {
        this.name = name;
        this.password = password;
        this.idUser = idUser;
        
    }
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getIdUser() {
        return idUser;
    }

    public void setIdUser(char idUser) {
        this.idUser = idUser;
    }

    
    
}
