package com.example.carapp;

import java.io.Serializable;
import java.util.UUID;

/**
 * User.java - Classe responsável pelo usuário
 * @author  Gregory Perozzo
 * @version 1.0
 */
public class User implements Serializable {

    private String _id, _name, _email, _password, _photo;

    public User() {

    }

    public User(String name, String email, String password) {
        _id = UUID.randomUUID().toString();
        _name = name;
        _email = email;
        _password = password;
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        _email = email;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;
    }

    public String getPhoto() {
        return _photo;
    }

    public void setPhoto(String photo) {
        _photo = photo;
    }
}
